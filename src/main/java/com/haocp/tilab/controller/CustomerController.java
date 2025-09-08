package com.haocp.tilab.controller;

import com.haocp.tilab.dto.ApiResponse;
import com.haocp.tilab.dto.request.Customer.AddCustomerAddressRequest;
import com.haocp.tilab.dto.request.Customer.LoginRequest;
import com.haocp.tilab.dto.request.Customer.RegisterRequest;
import com.haocp.tilab.dto.response.Cart.CartResponse;
import com.haocp.tilab.dto.response.Customer.CustomerAddressResponse;
import com.haocp.tilab.dto.response.Customer.CustomerResponse;
import com.haocp.tilab.dto.response.Order.OrderResponse;
import com.haocp.tilab.dto.response.User.UserResponse;
import com.haocp.tilab.enums.OrderStatus;
import com.haocp.tilab.service.CartService;
import com.haocp.tilab.service.CustomerService;
import com.haocp.tilab.service.OrderService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public ApiResponse<Page<OrderResponse>> getAllMyOrder(@RequestParam(required = false)OrderStatus status,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<Page<OrderResponse>>builder()
                .data(orderService.getAllMyOrder(status, page, size))
                .build();
    }

    @GetMapping("/me/carts")
    public ApiResponse<Page<CartResponse>> getAllMyCart(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<Page<CartResponse>>builder()
                .data(cartService.getAllMyCart(page, size))
                .build();
    }

    @PostMapping("/me/addresses")
    public ApiResponse<CustomerAddressResponse> addCustomerAddress(@RequestBody AddCustomerAddressRequest request) {
        return ApiResponse.<CustomerAddressResponse>builder()
                .data(customerService.addCustomerAddress(request))
                .build();
    }

    @GetMapping("/me/addresses")
    public ApiResponse<List<CustomerAddressResponse>> getCustomerAddress() {
        return ApiResponse.<List<CustomerAddressResponse>>builder()
                .data(customerService.getAllAddress())
                .build();
    }

}
