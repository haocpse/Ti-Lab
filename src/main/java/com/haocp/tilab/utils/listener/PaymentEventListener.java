package com.haocp.tilab.utils.listener;

import com.haocp.tilab.service.PaymentService;
import com.haocp.tilab.utils.event.OrderCreatedEvent;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentEventListener {

    PaymentService paymentService;

    public PaymentEventListener(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @EventListener
    public void handleOrderCreatedEvent(OrderCreatedEvent event) {
        paymentService.createPayment(event.getOrder(), event.getMethod());
    }

}
