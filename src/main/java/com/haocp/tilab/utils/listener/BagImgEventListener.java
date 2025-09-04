package com.haocp.tilab.utils.listener;

import com.haocp.tilab.service.BagImgService;
import com.haocp.tilab.service.PaymentService;
import com.haocp.tilab.utils.event.BagCreatedEvent;
import com.haocp.tilab.utils.event.OrderCreatedEvent;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BagImgEventListener {

    BagImgService bagImgService;

    public BagImgEventListener( BagImgService bagImgService) {
        this.bagImgService = bagImgService;
    }

    @EventListener
    public void handleOrderCreatedEvent(BagCreatedEvent event) {
        bagImgService.saveImage(event.getBag(), event.getRequest());
    }

//    @EventListener
//    public void handleOrderCreatedEvent(BagImgUpdatedEvent event) {
//        bagImgService.saveImage(event.getBag(), event.getRequest());
//    }

}
