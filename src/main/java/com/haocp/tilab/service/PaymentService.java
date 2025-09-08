package com.haocp.tilab.service;

import com.haocp.tilab.dto.response.Payment.PaymentResponse;
import com.haocp.tilab.dto.response.Payment.QRPaymentResponse;
import com.haocp.tilab.entity.Order;
import com.haocp.tilab.enums.PayMethod;

public interface PaymentService {

    void createPayment(Order order, PayMethod method);
    QRPaymentResponse createQR(double amount, String paymentId);
    PaymentResponse getPaymentByOrderId(String orderId);
}
