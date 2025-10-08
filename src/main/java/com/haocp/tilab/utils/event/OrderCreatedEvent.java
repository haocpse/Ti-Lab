package com.haocp.tilab.utils.event;

import com.haocp.tilab.dto.response.Order.OrderDetailResponse;
import com.haocp.tilab.entity.Customer;
import com.haocp.tilab.entity.Order;
import com.haocp.tilab.entity.OrderDetail;
import com.haocp.tilab.entity.Payment;
import com.haocp.tilab.enums.PayMethod;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEvent;

import java.util.List;
import java.util.Set;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderCreatedEvent extends ApplicationEvent {
    Customer customer;
    Order order;
    PayMethod method;
    String email;
    String membershipTier;
    Set<OrderDetailResponse> orderDetails;

    public OrderCreatedEvent(Object source, Customer customer, Order order, PayMethod method, String email, String membershipTier, Set<OrderDetailResponse> orderDetails) {
        super(source);
        this.order = order;
        this.method = method;
        this.customer = customer;
        this.email = email;
        this.membershipTier = membershipTier;
        this.orderDetails = orderDetails;
    }
}
