package com.haocp.tilab.service;

import com.haocp.tilab.dto.request.SePay.SePayWebhookRequest;
import com.haocp.tilab.dto.response.Payment.*;
import com.haocp.tilab.entity.Order;
import com.haocp.tilab.enums.PayMethod;
import com.haocp.tilab.repository.Projection.PaymentSummary;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface PaymentService {

    void createPayment(Order order, PayMethod method);
    QRPaymentResponse createQR(double amount, String paymentId);
    PaymentResponse getPaymentByOrderId(String orderId);
    void sePayConfirm(String authorization, SePayWebhookRequest request);
    CheckPaymentStatusResponse checkPaymentStatus(String paymentId);
    QRPaymentResponse reCreateQR(String paymentId);
    Page<PaymentResponse> getPayments(int page, int size);

    List<PaymentSummary> getPaymentSummary(LocalDate from, LocalDate to);

    PaymentStatResponse getPaymentStat(String range);
    List<PaymentMethodStatResponse> getPaymentMethodStat(String range);
    PaymentStatOverviewResponse getPaymentStatOverview(String range);

}
