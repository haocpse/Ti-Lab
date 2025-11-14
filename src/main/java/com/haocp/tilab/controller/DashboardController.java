package com.haocp.tilab.controller;

import com.haocp.tilab.dto.ApiResponse;
import com.haocp.tilab.dto.response.Bag.BestSellingBagsResponse;
import com.haocp.tilab.dto.response.Order.OrderStatByStatusResponse;
import com.haocp.tilab.dto.response.Order.OrderStatResponse;
import com.haocp.tilab.dto.response.Payment.PaymentMethodStatResponse;
import com.haocp.tilab.dto.response.Payment.PaymentStatOverviewResponse;
import com.haocp.tilab.dto.response.Payment.PaymentStatResponse;
import com.haocp.tilab.service.BagService;
import com.haocp.tilab.service.OrderService;
import com.haocp.tilab.service.PaymentService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/dashboards")
@PreAuthorize("hasAuthority('ADMIN')")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DashboardController {

    @Autowired
    OrderService orderService;
    @Autowired
    BagService bagService;
    @Autowired
    PaymentService paymentService;

    @GetMapping("/order-stat")
    public ApiResponse<OrderStatResponse> getOrderStat(@RequestParam(defaultValue = "1w") String range) {
        return ApiResponse.<OrderStatResponse>builder()
                .code(200)
                .message("Get order stat successfully")
                .data(orderService.getOrderStat(range))
                .build();
    }

    @GetMapping("/order-stat-status")
    public ApiResponse<List<OrderStatByStatusResponse>> getOrderStatStatus(@RequestParam(defaultValue = "1w") String range) {
        return ApiResponse.<List<OrderStatByStatusResponse>>builder()
                .code(200)
                .message("Get order stat status successfully")
                .data(orderService.getOrderStatByStatus(range))
                .build();
    }

    @GetMapping("/best-selling")
    public ApiResponse<List<BestSellingBagsResponse>> getBestSellingBags(@RequestParam(defaultValue = "1w") String range) {
        return ApiResponse.<List<BestSellingBagsResponse>>builder()
                .code(200)
                .message("Get best selling bags successfully")
                .data(bagService.getBestSellingBags(range))
                .build();
    }

    @GetMapping("/payment-stat")
    public ApiResponse<PaymentStatResponse> getPaymentStat(@RequestParam(defaultValue = "1w") String range) {
        return ApiResponse.<PaymentStatResponse>builder()
                .code(200)
                .message("Get payment stat successfully")
                .data(paymentService.getPaymentStat(range))
                .build();
    }

    @GetMapping("/payment-method-stat")
    public ApiResponse<List<PaymentMethodStatResponse>> getPaymentMethodStat(@RequestParam(defaultValue = "1w") String range) {
        return ApiResponse.<List<PaymentMethodStatResponse>>builder()
                .code(200)
                .message("Get payment method stat successfully")
                .data(paymentService.getPaymentMethodStat(range))
                .build();
    }

    @GetMapping("/payment-stat-overview")
    public ApiResponse<PaymentStatOverviewResponse> getPaymentStatOverview(@RequestParam(defaultValue = "1w") String range) {
        return ApiResponse.<PaymentStatOverviewResponse>builder()
                .code(200)
                .message("Get payment stat overview successfully")
                .data(paymentService.getPaymentStatOverview(range))
                .build();
    }
}
