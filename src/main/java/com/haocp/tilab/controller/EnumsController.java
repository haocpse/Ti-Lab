package com.haocp.tilab.controller;

import com.haocp.tilab.dto.ApiResponse;
import com.haocp.tilab.enums.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/enums")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EnumsController {

    @GetMapping("/bag-status")
    public ApiResponse<List<String>> bagStatus() {
        return ApiResponse.<List<String>>builder()
                .data(Arrays.stream(BagStatus.values())
                        .map(Enum::name)
                        .toList())
                .build();
    }

    @GetMapping("/bag-type")
    public ApiResponse<List<String>> bagType() {
        return ApiResponse.<List<String>>builder()
                .data(Arrays.stream(BagType.values())
                        .map(Enum::name)
                        .toList())
                .build();
    }

    @GetMapping("/coupon-status")
    public ApiResponse<List<String>> couponStatus() {
        return ApiResponse.<List<String>>builder()
                .data(Arrays.stream(CouponStatus.values())
                        .map(Enum::name)
                        .toList())
                .build();
    }

    @GetMapping("/order-status")
    public ApiResponse<List<String>> orderStatus() {
        return ApiResponse.<List<String>>builder()
                .data(Arrays.stream(OrderStatus.values())
                        .map(Enum::name)
                        .toList())
                .build();
    }

    @GetMapping("/payment-status")
    public ApiResponse<List<String>> paymentStatus() {
        return ApiResponse.<List<String>>builder()
                .data(Arrays.stream(PaymentStatus.values())
                        .map(Enum::name)
                        .toList())
                .build();
    }

    @GetMapping("/pay-method")
    public ApiResponse<List<String>> payMethod() {
        return ApiResponse.<List<String>>builder()
                .data(Arrays.stream(PayMethod.values())
                        .map(Enum::name)
                        .toList())
                .build();
    }

    @GetMapping("/staff-role")
    public ApiResponse<List<String>> staffRole() {
        return ApiResponse.<List<String>>builder()
                .data(Arrays.stream(StaffRole.values())
                        .map(Enum::name)
                        .toList())
                .build();
    }

    @GetMapping("/user-role")
    public ApiResponse<List<String>> userRole() {
        return ApiResponse.<List<String>>builder()
                .data(Arrays.stream(UserRole.values())
                        .map(Enum::name)
                        .toList())
                .build();
    }

}
