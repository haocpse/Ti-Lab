package com.haocp.tilab.utils.event;

import com.haocp.tilab.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEvent;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GenerateTokenLoginEvent extends ApplicationEvent {

    String jwtId;
    int refreshDuration;
    User user;

    public GenerateTokenLoginEvent(Object source, String jwtId, int refreshDuration, User user) {
        super(source);
        this.jwtId = jwtId;
        this.refreshDuration = refreshDuration;
        this.user = user;
    }

}
