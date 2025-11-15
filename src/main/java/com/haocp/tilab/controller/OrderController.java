package com.haocp.tilab.controller;

import com.haocp.tilab.dto.ApiResponse;
import com.haocp.tilab.dto.request.Order.CreateOrderRequest;
import com.haocp.tilab.dto.response.Order.OrderResponse;
import com.haocp.tilab.service.OrderService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping
    public ApiResponse<OrderResponse> createOrder(@RequestBody CreateOrderRequest request) {
        return ApiResponse.<OrderResponse>builder()
                .data(orderService.createOrder(request))
                .build();
    }

    @GetMapping
    public ApiResponse<Page<OrderResponse>> getAllOrder(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size,
                                                        @RequestParam Boolean unCompleted) {
        return ApiResponse.<Page<OrderResponse>>builder()
                .data(orderService.getAllOrder(page, size, unCompleted))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<OrderResponse> getOrder(@PathVariable String id) {
        return ApiResponse.<OrderResponse>builder()
                .code(200)
                .data(orderService.getOrderById(id))
                .message("Get order by id successfully")
                .build();
    }

    @PutMapping("/{id}/complete")
    public ApiResponse<OrderResponse> completeOrder(@PathVariable String id) {
        return ApiResponse.<OrderResponse>builder()
                .code(200)
                .data(orderService.completeOrder(id))
                .message("Complete order by id successfully")
                .build();
    }
}
