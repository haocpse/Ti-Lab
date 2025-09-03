package com.haocp.tilab.controller;

import com.haocp.tilab.dto.ApiResponse;
import com.haocp.tilab.dto.request.Customer.LoginRequest;
import com.haocp.tilab.dto.request.Customer.RegisterRequest;
import com.haocp.tilab.dto.response.Cart.CartResponse;
import com.haocp.tilab.dto.response.Customer.CustomerResponse;
import com.haocp.tilab.dto.response.Order.OrderResponse;
import com.haocp.tilab.dto.response.User.UserResponse;
import com.haocp.tilab.service.CartService;
import com.haocp.tilab.service.CustomerService;
import com.haocp.tilab.service.OrderService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerController {

    @Autowired
    CustomerService customerService;
    @Autowired
    OrderService orderService;
    @Autowired
    CartService cartService;

    @GetMapping
    public ApiResponse<List<CustomerResponse>> getAllCustomer() {
        return ApiResponse.<List<CustomerResponse>>builder()
                .data(customerService.getAllCustomer())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<CustomerResponse> getCustomerById(@PathVariable String id) {
        return ApiResponse.<CustomerResponse>builder()
                .data(customerService.getCustomerById(id))
                .build();
    }

    @GetMapping("/me/orders")
    public ApiResponse<List<OrderResponse>> getAllMyOrder() {
        return ApiResponse.<List<OrderResponse>>builder()
                .data(orderService.getAllMyOrder())
                .build();
    }

    @GetMapping("/me/carts")
    public ApiResponse<List<CartResponse>> getAllMyCart() {
        return ApiResponse.<List<CartResponse>>builder()
                .data(cartService.getAllMyCart())
                .build();
    }
}
