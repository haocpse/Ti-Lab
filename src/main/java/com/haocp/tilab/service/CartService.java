package com.haocp.tilab.service;

import com.haocp.tilab.dto.request.Cart.AddToCartRequest;
import com.haocp.tilab.dto.response.Cart.CartNumberResponse;
import com.haocp.tilab.dto.response.Cart.CartResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CartService {

    CartResponse addToCart(AddToCartRequest request);
    Page<CartResponse> getAllCart(int page, int size);
    Page<CartResponse> getAllMyCart(int page, int size);
    void deleteCartById(String cartId);
    CartNumberResponse getCartNumber();
}
