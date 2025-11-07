package com.haocp.tilab.repository.Projection;

import com.haocp.tilab.enums.OrderStatus;

public interface OrderStatusSummary {

    OrderStatus getGroupedStatus();
    int getTotal();

}
