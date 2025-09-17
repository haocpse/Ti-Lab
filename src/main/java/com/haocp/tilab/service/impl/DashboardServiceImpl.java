package com.haocp.tilab.service.impl;

import com.haocp.tilab.dto.response.Dashboard.BagDashboardResponse;
import com.haocp.tilab.dto.response.Dashboard.OrderDetailDashboardResponse;
import com.haocp.tilab.dto.response.Dashboard.OrderSnapshotDashboardResponse;
import com.haocp.tilab.dto.response.Dashboard.PaymentDashboardResponse;
import com.haocp.tilab.enums.BagStatus;
import com.haocp.tilab.enums.BagType;
import com.haocp.tilab.enums.OrderStatus;
import com.haocp.tilab.enums.PayMethod;
import com.haocp.tilab.repository.Projection.OrderSummary;
import com.haocp.tilab.repository.Projection.PaymentSummary;
import com.haocp.tilab.service.BagService;
import com.haocp.tilab.service.DashboardService;
import com.haocp.tilab.service.OrderService;
import com.haocp.tilab.service.PaymentService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    BagService bagService;
    @Autowired
    PaymentService paymentService;
    @Autowired
    OrderService orderService;

    @Override
    public BagDashboardResponse getBagDashboard() {
        return BagDashboardResponse.builder()
                .coreBag(bagService.totalBagByTypeAndStatus(BagType.CORE_COLLECTION, BagStatus.DELETED))
                .artistBag(bagService.totalBagByTypeAndStatus(BagType.ARTIST_COLLECTION, BagStatus.DELETED))
                .comingBag(bagService.totalBagByTypeAndStatus(null, BagStatus.IN_COMING))
                .inSaleBag(bagService.totalBagByTypeAndStatus(null, BagStatus.IN_SALE))
                .deleteBag(bagService.totalBagByTypeAndStatus(null, BagStatus.DELETED))
                .totalBag(bagService.totalBagByTypeAndStatus(null, null))
                .build();
    }

    @Override
    public List<PaymentDashboardResponse> getPaymentDashboard(LocalDate from, LocalDate to) {
        List<PaymentSummary> summaries = paymentService.getPaymentSummary(from, to);
        Map<String, PaymentDashboardResponse> map = new LinkedHashMap<>();
        for (PaymentSummary s : summaries) {
            String key = s.getYear() + "-" + s.getMonth();
            PaymentDashboardResponse response = map.getOrDefault(key,
                    PaymentDashboardResponse.builder()
                            .year(s.getYear())
                            .month(s.getMonth())
                            .codTotal(0.0)
                            .cardTotal(0.0)
                            .build()
            );

            if (s.getMethod() == PayMethod.COD) {
                response.setCodTotal(s.getTotal());
            } else if (s.getMethod() == PayMethod.CARD) {
                response.setCardTotal(s.getTotal());
            }

            map.put(key, response);
        }
        return new ArrayList<>(map.values());
    }

    @Override
    public OrderSnapshotDashboardResponse getOrderSnapshotDashboard() {
        return OrderSnapshotDashboardResponse.builder()
                .processingOrder(orderService.totalOrderByStatus(List.of(OrderStatus.PREPARING, OrderStatus.DELIVERING)))
                .completedOrder(orderService.totalOrderByStatus(List.of(OrderStatus.COMPLETED, OrderStatus.DELIVERED)))
                .failedOrder(orderService.totalOrderByStatus(List.of(OrderStatus.FAILED, OrderStatus.CANCELLED)))
                .afterSalesOrder(orderService.totalOrderByStatus(List.of(OrderStatus.REFUNDED, OrderStatus.RETURNED)))
                .build();
    }

    @Override
    public List<OrderDetailDashboardResponse> getOrderDetailDashboard(LocalDate from, LocalDate to) {
        List<OrderSummary> summaries = orderService.getOrderSummary(from, to);

        Map<String, OrderDetailDashboardResponse> map = new LinkedHashMap<>();

        for (OrderSummary s : summaries) {
            String key = s.getYear() + "-" + s.getMonth();

            OrderDetailDashboardResponse response = map.getOrDefault(key,
                    OrderDetailDashboardResponse.builder()
                            .year(s.getYear())
                            .month(s.getMonth())
                            .processingOrder(0)
                            .completedOrder(0)
                            .failedOrder(0)
                            .afterSalesOrder(0)
                            .build()
            );

            switch (s.getStatus()) {
                case OrderStatus.PREPARING, OrderStatus.DELIVERING -> response.setProcessingOrder(s.getTotal());
                case OrderStatus.COMPLETED, OrderStatus.DELIVERED   -> response.setCompletedOrder(s.getTotal());
                case OrderStatus.CANCELLED, OrderStatus.FAILED   -> response.setFailedOrder(s.getTotal());
                case OrderStatus.REFUNDED, OrderStatus.RETURNED   -> response.setAfterSalesOrder(s.getTotal());
            }

            map.put(key, response);
        }

        return new ArrayList<>(map.values());
    }
}
