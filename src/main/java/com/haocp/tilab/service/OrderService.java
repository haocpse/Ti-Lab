package com.haocp.tilab.service;

import com.haocp.tilab.dto.request.Cart.AddToCartRequest;
import com.haocp.tilab.dto.request.Order.CreateOrderRequest;
import com.haocp.tilab.dto.response.Cart.CartResponse;
import com.haocp.tilab.dto.response.Order.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(CreateOrderRequest request);
    List<OrderResponse> getAllOrder();
    List<OrderResponse> getAllMyOrder(String username);

}
