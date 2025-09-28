package com.haocp.tilab.service;

import com.haocp.tilab.dto.request.SePay.SePayWebhookRequest;
import com.haocp.tilab.dto.response.Payment.CheckPaymentStatusResponse;
import com.haocp.tilab.dto.response.Payment.PaymentResponse;
import com.haocp.tilab.dto.response.Payment.QRPaymentResponse;
import com.haocp.tilab.entity.Order;
import com.haocp.tilab.enums.PayMethod;
import com.haocp.tilab.repository.Projection.PaymentSummary;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface PaymentService {

    void createPayment(Order order, PayMethod method);
    QRPaymentResponse createQR(double amount, String paymentId);
    PaymentResponse getPaymentByOrderId(String orderId);
    void sePayConfirm(String authorization, SePayWebhookRequest request);
    CheckPaymentStatusResponse checkPaymentStatus(String paymentId);

    List<PaymentSummary> getPaymentSummary(LocalDate from, LocalDate to);

}
