package com.haocp.tilab.utils.event;

import com.haocp.tilab.dto.request.Bag.SaveImageBagRequest;
import com.haocp.tilab.entity.Bag;
import com.haocp.tilab.entity.Order;
import com.haocp.tilab.enums.PayMethod;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEvent;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BagCreatedEvent extends ApplicationEvent {

    Bag bag;
    SaveImageBagRequest request;

    public BagCreatedEvent(Object source, Bag bag, SaveImageBagRequest request) {
        super(source);
        this.bag = bag;
        this.request = request;
    }

}
