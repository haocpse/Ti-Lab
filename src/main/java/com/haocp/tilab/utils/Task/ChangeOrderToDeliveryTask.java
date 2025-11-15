package com.haocp.tilab.utils.Task;

import com.haocp.tilab.entity.Order;
import com.haocp.tilab.entity.VerificationToken;
import com.haocp.tilab.enums.OrderStatus;
import com.haocp.tilab.repository.OrderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ChangeOrderToDeliveryTask {

    private final OrderRepository orderRepository;

    @Scheduled(fixedRate = 43200000)
    public void checkAndDeleteExpiredTokens() {
        Instant now = Instant.now();
        List<Order> orders = orderRepository
                .findOrderByCreatedAtAfterAndStatusIs(now, OrderStatus.PREPARING);
        for (Order order : orders) {
            order.setStatus(OrderStatus.DELIVERING);
            orderRepository.save(order);
        }
    }

}
