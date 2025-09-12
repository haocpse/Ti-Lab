package com.haocp.tilab.utils.event;

import com.haocp.tilab.entity.Order;
import com.haocp.tilab.entity.User;
import com.haocp.tilab.enums.PayMethod;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEvent;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PasswordResetEvent extends ApplicationEvent {

    User user;

    public PasswordResetEvent(Object source, User user) {
        super(source);
        this.user = user;
    }

}
