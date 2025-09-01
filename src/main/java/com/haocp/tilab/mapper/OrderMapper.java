package com.haocp.tilab.mapper;

import com.haocp.tilab.dto.request.Order.CreateOrderDetailRequest;
import com.haocp.tilab.dto.request.Order.CreateOrderRequest;
import com.haocp.tilab.dto.response.Order.OrderDetailResponse;
import com.haocp.tilab.dto.response.Order.OrderResponse;
import com.haocp.tilab.entity.Order;
import com.haocp.tilab.entity.OrderDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toOrder(CreateOrderRequest request);
    OrderResponse toResponse(Order order);

}
