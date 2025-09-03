package com.haocp.tilab.service;

import com.haocp.tilab.dto.request.Payment.CreatePaymentRequest;
import com.haocp.tilab.dto.response.Payment.PaymentResponse;
import com.haocp.tilab.entity.Payment;

public interface PaymentService {

    PaymentResponse createPayment(CreatePaymentRequest request);
    PaymentResponse getPaymentByOrderId(String orderId);
}
