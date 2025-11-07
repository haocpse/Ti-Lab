package com.haocp.tilab.controller;

import com.haocp.tilab.dto.ApiResponse;
import com.haocp.tilab.dto.response.Order.OrderStatByStatusResponse;
import com.haocp.tilab.dto.response.Order.OrderStatResponse;
import com.haocp.tilab.service.OrderService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
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

}
