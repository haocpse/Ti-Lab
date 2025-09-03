package com.haocp.tilab.utils.event;

import com.haocp.tilab.entity.Order;
import com.haocp.tilab.enums.PayMethod;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEvent;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderCreatedEvent extends ApplicationEvent {
    Order order;
    PayMethod method;

    public OrderCreatedEvent(Object source, Order order, PayMethod method) {
        super(source);
        this.order = order;
        this.method = method;
    }
}
