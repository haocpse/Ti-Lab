package com.haocp.tilab.service;

import com.haocp.tilab.dto.request.Cart.AddToCartRequest;
import com.haocp.tilab.dto.request.Order.CreateOrderRequest;
import com.haocp.tilab.dto.response.Cart.CartResponse;
import com.haocp.tilab.dto.response.Order.OrderResponse;
import com.haocp.tilab.enums.OrderStatus;
import com.haocp.tilab.repository.Projection.OrderSummary;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    OrderResponse createOrder(CreateOrderRequest request);
    Page<OrderResponse> getAllOrder(int page, int size);
    Page<OrderResponse> getAllMyOrder(OrderStatus status, int page, int size);

    int totalOrderByStatus(List<OrderStatus> statuses);
    List<OrderSummary> getOrderSummary(LocalDate from, LocalDate to);
}
