package com.haocp.tilab.utils.event;

import com.haocp.tilab.entity.Customer;
import com.haocp.tilab.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEvent;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ConfirmRegisterEvent extends ApplicationEvent {

    String token;
    Customer customer;

    public ConfirmRegisterEvent(Object source, String token, Customer customer) {
        super(source);
        this.token = token;
        this.customer = customer;
    }

}
