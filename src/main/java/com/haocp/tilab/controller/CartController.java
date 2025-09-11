package com.haocp.tilab.controller;

import com.haocp.tilab.dto.ApiResponse;
import com.haocp.tilab.dto.request.Cart.AddToCartRequest;
import com.haocp.tilab.dto.response.Cart.CartResponse;
import com.haocp.tilab.service.CartService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping
    public ApiResponse<CartResponse> addToCart(@RequestBody AddToCartRequest request) {
        return ApiResponse.<CartResponse>builder()
                .data(cartService.addToCart(request))
                .build();
    }

    @DeleteMapping("{id}")
    public ApiResponse<Void> deleteCart(@PathVariable String id) {
        cartService.deleteCartById(id);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping
    public ApiResponse<Page<CartResponse>> getAllCart(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<Page<CartResponse>>builder()
                .data(cartService.getAllCart(page, size))
                .build();
    }


}
