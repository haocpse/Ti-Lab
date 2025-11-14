package com.haocp.tilab.controller;

import com.haocp.tilab.dto.ApiResponse;
import com.haocp.tilab.dto.request.SePay.SePayWebhookRequest;
import com.haocp.tilab.dto.response.Payment.CheckPaymentStatusResponse;
import com.haocp.tilab.dto.response.Payment.PaymentResponse;
import com.haocp.tilab.dto.response.Payment.QRPaymentResponse;
import com.haocp.tilab.dto.response.SePay.SePayResponse;
import com.haocp.tilab.entity.Payment;
import com.haocp.tilab.service.PaymentService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ApiResponse<Page<PaymentResponse>> getPayments(@RequestParam int page, @RequestParam int size) {
        return ApiResponse.<Page<PaymentResponse>>builder()
                .data(paymentService.getPayments(page - 1, size))
                .build();
    }


    @PostMapping("/confirm")
    public ResponseEntity<SePayResponse> confirm(@RequestHeader("Authorization") String authorization,
                                                 @RequestBody SePayWebhookRequest request) {
        paymentService.sePayConfirm(authorization, request);
        return  ResponseEntity.status(HttpStatus.CREATED)
                .body(new SePayResponse(true));
    }

    @GetMapping("/{id}/check-status")
    public ApiResponse<CheckPaymentStatusResponse> getPayment(@PathVariable String id) {
        return ApiResponse.<CheckPaymentStatusResponse>builder()
                .data(paymentService.checkPaymentStatus(id))
                .build();
    }

    @PutMapping("/{id}/re-create")
    public ApiResponse<QRPaymentResponse> reCreatePayment(@PathVariable String id) {
        return ApiResponse.<QRPaymentResponse>builder()
                .data(paymentService.reCreateQR(id))
                .build();
    }
}
