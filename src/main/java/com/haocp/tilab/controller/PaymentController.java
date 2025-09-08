package com.haocp.tilab.controller;

import com.haocp.tilab.dto.ApiResponse;
import com.haocp.tilab.dto.response.Payment.QRPaymentResponse;
import com.haocp.tilab.service.PaymentService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments/")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping
    public ApiResponse<QRPaymentResponse> createQR(@RequestParam String paymentId, @RequestParam double amount) {
        return ApiResponse.<QRPaymentResponse>builder()
                .data(paymentService.createQR(amount, paymentId))
                .build();
    }

    @PostMapping("confirm")
    public ApiResponse<QRPaymentResponse> confirm() {
        return ApiResponse.<QRPaymentResponse>builder()
                .build();
    }
}
