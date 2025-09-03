package com.haocp.tilab.service;

import com.haocp.tilab.dto.request.Cart.AddToCartRequest;
import com.haocp.tilab.dto.response.Cart.CartResponse;

import java.util.List;

public interface CartService {

    CartResponse addToCart(AddToCartRequest request);
    List<CartResponse> getAllCart();
    List<CartResponse> getAllMyCart();
    void deleteCartById(String cartId);
}
