package com.haocp.tilab.service.impl;

import com.haocp.tilab.dto.request.SePay.SePayWebhookRequest;
import com.haocp.tilab.dto.response.Payment.*;
import com.haocp.tilab.entity.Customer;
import com.haocp.tilab.entity.Order;
import com.haocp.tilab.entity.Payment;
import com.haocp.tilab.entity.User;
import com.haocp.tilab.dto.response.Token.VerificationTokenResponse;
import com.haocp.tilab.enums.PayMethod;
import com.haocp.tilab.enums.PaymentStatus;
import com.haocp.tilab.enums.TokenType;
import com.haocp.tilab.exception.AppException;
import com.haocp.tilab.exception.ErrorCode;
import com.haocp.tilab.mapper.PaymentMapper;
import com.haocp.tilab.repository.PaymentRepository;
import com.haocp.tilab.repository.Projection.PaymentStatOverview;
import com.haocp.tilab.repository.Projection.PaymentSummary;
import com.haocp.tilab.repository.UserRepository;
import com.haocp.tilab.service.PaymentService;
import com.haocp.tilab.service.VerificationTokenService;
import com.haocp.tilab.utils.CommonHelper;
import com.haocp.tilab.utils.IdentifyUser;
import com.haocp.tilab.utils.WeekRangeUtil;
import com.haocp.tilab.utils.event.ConfirmPaidEvent;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    PaymentMapper paymentMapper;
    @Value("${app.qr}")
    String urlQR;
    @Value("${app.api.webhook}")
    String exceptedKey;
    @Autowired
    VerificationTokenService verificationTokenService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    CommonHelper commonHelper;

    @Override
    public void createPayment(Order order, PayMethod method) {
        PaymentStatus status = PaymentStatus.UNPAID;
        if (method.equals(PayMethod.CARD)) {
            status = PaymentStatus.PROCESSING;
        }
        paymentRepository.save(Payment.builder()
                .total(order.getTotal())
                .order(order)
                .status(status)
                .method(method)
                .build());
    }

    @Override
    public QRPaymentResponse createQR(double amount, String paymentId) {
        User user = userRepository.findByUsernameAndActiveIsTrue(IdentifyUser.getCurrentUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_FOUND));
        if (Double.compare(payment.getTotal(), amount) != 0) {
            throw new AppException(ErrorCode.AMOUNT_NOT_MATCH_TOTAL);
        }
        String referenceId = "paymentId=" + paymentId;
        String token = verificationTokenService.createToken(TokenType.PAYMENT_PROCESSING, user, referenceId);
        String url = urlQR
                .replace("-amount-", String.format("%.2f", amount))
                .replace("-payment-", token);
        return QRPaymentResponse.builder()
                .urlQR(url)
                .build();
    }

    @Override
    public PaymentResponse getPaymentByOrderId(String orderId) {
        Payment payment = paymentRepository.findPaymentByOrder_Id(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.NO_PAYMENT_SUITABLE));
        return paymentMapper.toResponse(payment);
    }

    @Override
    @Transactional
    public void sePayConfirm(String authorization, SePayWebhookRequest request) {
        UUID token = validAuthorization(authorization, request.getContent());
        VerificationTokenResponse verificationTokenResponse = verificationTokenService.validateToken(String.valueOf(token));
        if (verificationTokenResponse.isValid()) {
            String paymentId = verificationTokenResponse.getReferenceId().replace("paymentId=", "");
            Payment payment = paymentRepository.findById(paymentId)
                    .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_FOUND));
            payment.setStatus(PaymentStatus.PAID);
            payment.setPayAt(verificationTokenResponse.getUsedAt());
            paymentRepository.save(payment);
            if (payment.getStatus().equals(PaymentStatus.PAID)) {
                Order order = payment.getOrder();
                Customer customer = order.getCustomer();
                User user = customer.getUser();
                applicationEventPublisher.publishEvent(new ConfirmPaidEvent(this, customer, order, payment, user.getEmail(), customer.getMembership().getName()));
            }
        }
    }

    @Override
    public CheckPaymentStatusResponse checkPaymentStatus(String paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_FOUND));
        return paymentMapper.toCheckResponse(payment);
    }

    @Override
    public QRPaymentResponse reCreateQR(String paymentId) {
        User user = userRepository.findByUsernameAndActiveIsTrue(IdentifyUser.getCurrentUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_FOUND));
        payment.setStatus(PaymentStatus.PROCESSING);
        String referenceId = "paymentId=" + paymentId;
        String token = verificationTokenService.createToken(TokenType.PAYMENT_PROCESSING, user, referenceId);
        String url = urlQR
                .replace("-amount-", String.format("%.2f", payment.getTotal()))
                .replace("-payment-", token);
        return QRPaymentResponse.builder()
                .urlQR(url)
                .build();
    }

    @Override
    @Transactional
    public Page<PaymentResponse> getPayments(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Payment> payments = paymentRepository.findPaymentByStatusOrderByPayAtDesc(PaymentStatus.PAID, pageable);
        return payments.map(payment -> {
            Order order = payment.getOrder();
           return PaymentResponse.builder()
                   .paymentId(payment.getId())
                   .phone(order.getPhone())
                   .fullCustomerName(order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName())
                   .total(payment.getTotal())
                   .paymentDate(payment.getPayAt())
                   .method(payment.getMethod())
                   .status(payment.getStatus())
                   .build();
        });
    }

    @Override
    public List<PaymentSummary> getPaymentSummary(LocalDate from, LocalDate to) {
        return paymentRepository.getPaymentSummary(from, to);
    }

    @Override
    public PaymentStatResponse getPaymentStat(String range) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime from = commonHelper.getFromDate(range, now);
        long daysBetween = ChronoUnit.DAYS.between(from, now);
        List<PaymentStatDetailResponse> details;
        String type;
        if (daysBetween <= 31) {
            type = "DAY";
            details = paymentRepository.getPaymentStatsByDay(from, now)
                    .stream()
                    .map(ps -> PaymentStatDetailResponse.builder()
                            .totalPayments(ps.getTotalPayments())
                            .amount(ps.getAmount())
                            .period(ps.getPeriod())
                            .build())
                    .toList();
        } else if (daysBetween <= 92) {
            type = "WEEK";
            details = paymentRepository.getPaymentStatsByWeek(from, now)
                    .stream()
                    .map(ps -> PaymentStatDetailResponse.builder()
                            .totalPayments(ps.getTotalPayments())
                            .amount(ps.getAmount())
                            .period(WeekRangeUtil.formatYearWeek(ps.getPeriod()))
                            .build())
                    .toList();
        } else {
            type = "MONTH";
            details = paymentRepository.getPaymentStatsByMonth(from, now)
                    .stream()
                    .map(ps -> PaymentStatDetailResponse.builder()
                            .totalPayments(ps.getTotalPayments())
                            .amount(ps.getAmount())
                            .period(ps.getPeriod())
                            .build())
                    .toList();
        }
        return PaymentStatResponse.builder()
                .typeRange(type)
                .details(details)
                .build();
    }

    @Override
    public List<PaymentMethodStatResponse> getPaymentMethodStat(String range) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime from = commonHelper.getFromDate(range, now);
        return paymentRepository.getPaymentMethodStats(from, now)
                .stream()
                .map(pm -> PaymentMethodStatResponse.builder()
                        .payMethod(pm.getMethod())
                        .percentage(pm.getPercentage())
                        .total(pm.getTotal())
                        .build())
                .toList();
    }

    @Override
    public PaymentStatOverviewResponse getPaymentStatOverview(String range) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime from = commonHelper.getFromDate(range, now);
        PaymentStatOverview pso = paymentRepository.getPaymentOverview(from, now);
        return PaymentStatOverviewResponse.builder()
                .total(pso.getTotal())
                .totalPrice(pso.getTotalPrice())
                .avgPrice(pso.getAvgPrice())
                .build();
    }

    UUID validAuthorization(String authorization, String content) {
        if (authorization == null || !authorization.startsWith("Apikey ")) {
            throw new AppException(ErrorCode.API_WEBHOOK_MISSING);
        }
        String apiKey = authorization.substring("Apikey ".length());
        if (!exceptedKey.equals(apiKey)) {
            throw new AppException(ErrorCode.INVALID_API_WEBHOOK);
        }
        Pattern TOKEN_PATTERN = Pattern.compile("TKPEXE([0-9a-fA-F]{32})");
        Matcher matcher = TOKEN_PATTERN.matcher(content);
        if (!matcher.find()) {
            throw new AppException(ErrorCode.INVALID_API_WEBHOOK);
        }
        String rawToken = matcher.group(1);
        String formatted = rawToken.replaceFirst(
                "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{12})",
                "$1-$2-$3-$4-$5"
        );
        return UUID.fromString(formatted);
    }
}
