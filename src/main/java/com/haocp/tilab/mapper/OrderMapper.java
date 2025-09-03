package com.haocp.tilab.mapper;

import com.haocp.tilab.dto.request.Order.CreateOrderDetailRequest;
import com.haocp.tilab.dto.request.Order.CreateOrderRequest;
import com.haocp.tilab.dto.response.Customer.CustomerInOrderResponse;
import com.haocp.tilab.dto.response.Order.OrderDetailResponse;
import com.haocp.tilab.dto.response.Order.OrderResponse;
import com.haocp.tilab.entity.Customer;
import com.haocp.tilab.entity.Order;
import com.haocp.tilab.entity.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CouponMapper.class})
public interface OrderMapper {

    Order toOrder(CreateOrderRequest request);

    @Mapping(target = "couponResponse", source = "coupon")
    @Mapping(target = "customerResponse", expression = "java(toCustomerResponse(order.getCustomer()))")
    OrderResponse toResponse(Order order);

    @Mapping(target = "customerResponse", expression = "java(toCustomerResponse(order.getCustomer()))")
    @Mapping(target = "couponResponse", ignore = true)
    OrderResponse toResponseWithoutCoupon(Order order);

    default CustomerInOrderResponse toCustomerResponse(Customer customer) {
        if (customer == null) return null;
        return CustomerInOrderResponse.builder()
                .customerId(customer.getId())
                .fullName(customer.getFirstName() + " " + customer.getLastName())
                .phone(customer.getUser().getPhone())
                .email(customer.getUser().getEmail())
                .build();
    }

}
