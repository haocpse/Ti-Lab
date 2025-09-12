package com.haocp.tilab.controller;

import com.haocp.tilab.dto.ApiResponse;
import com.haocp.tilab.dto.request.SePay.SePayWebhookRequest;
import com.haocp.tilab.dto.response.Payment.QRPaymentResponse;
import com.haocp.tilab.service.PaymentService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
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

    @PostMapping("/confirm")
    public ApiResponse<Void> confirm(@RequestHeader("x-api-key") String apiKey,
                                     @RequestBody SePayWebhookRequest request) {
        paymentService.sePayConfirm(apiKey, request);
        return ApiResponse.<Void>builder().build();
    }
}
