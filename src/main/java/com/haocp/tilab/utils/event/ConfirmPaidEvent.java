package com.haocp.tilab.utils.event;

import com.haocp.tilab.entity.Customer;
import com.haocp.tilab.entity.Order;
import com.haocp.tilab.entity.Payment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEvent;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ConfirmPaidEvent extends ApplicationEvent {

    Customer customer;
    Order order;
    Payment payment;
    String email;
    String membershipTier;

    public ConfirmPaidEvent(Object source, Customer customer, Order order, Payment payment, String email, String membershipTier) {
        super(source);
        this.order = order;
        this.customer = customer;
        this.payment = payment;
        this.email = email;
        this.membershipTier = membershipTier;
    }

}
