package com.haocp.tilab.mapper;

import com.haocp.tilab.dto.request.Order.CreateOrderDetailRequest;
import com.haocp.tilab.dto.request.Order.CreateOrderRequest;
import com.haocp.tilab.dto.response.Order.OrderDetailResponse;
import com.haocp.tilab.dto.response.Order.OrderResponse;
import com.haocp.tilab.entity.Order;
import com.haocp.tilab.entity.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {

    OrderDetail toOrderDetail(CreateOrderDetailRequest request);

    @Mapping(target = "detailId", source = "id")
    OrderDetailResponse toResponse(OrderDetail orderDetail);
}
