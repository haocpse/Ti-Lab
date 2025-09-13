package com.haocp.tilab.service.impl;

import com.haocp.tilab.dto.request.SePay.SePayWebhookRequest;
import com.haocp.tilab.dto.response.Payment.PaymentResponse;
import com.haocp.tilab.dto.response.Payment.QRPaymentResponse;
import com.haocp.tilab.entity.Customer;
import com.haocp.tilab.entity.Order;
import com.haocp.tilab.entity.Payment;
import com.haocp.tilab.entity.User;
import com.haocp.tilab.dto.response.Token.VerificationTokenResponse;
import com.haocp.tilab.entity.*;
import com.haocp.tilab.enums.PayMethod;
import com.haocp.tilab.enums.PaymentStatus;
import com.haocp.tilab.enums.TokenType;
import com.haocp.tilab.exception.AppException;
import com.haocp.tilab.exception.ErrorCode;
import com.haocp.tilab.mapper.PaymentMapper;
import com.haocp.tilab.repository.OrderRepository;
import com.haocp.tilab.repository.PaymentRepository;
import com.haocp.tilab.repository.UserRepository;
import com.haocp.tilab.service.PaymentService;
import com.haocp.tilab.service.VerificationTokenService;
import com.haocp.tilab.utils.IdentifyUser;
import com.haocp.tilab.utils.event.ConfirmPaidEventListener;
import com.haocp.tilab.utils.event.PasswordResetEvent;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

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
        paymentRepository.findById(paymentId)
                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_FOUND));
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
        if(verificationTokenResponse.isValid()) {
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
                applicationEventPublisher.publishEvent(new ConfirmPaidEventListener(this, customer, order, payment, user.getEmail()));
            }
        }
    }

    UUID validAuthorization(String authorization, String content) {
        if (authorization == null || !authorization.startsWith("Apikey ")) {
            throw new AppException(ErrorCode.API_WEBHOOK_MISSING);
        }
        String apiKey = authorization.substring("Apikey ".length());
        if (!exceptedKey.equals(apiKey)){
            throw new AppException(ErrorCode.INVALID_API_WEBHOOK);
        }
        String rawToken = content.replace("TKPEXE", "");
        String formatted = rawToken.replaceFirst(
                "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{12})",
                "$1-$2-$3-$4-$5"
        );
        return UUID.fromString(formatted);
    }
}
