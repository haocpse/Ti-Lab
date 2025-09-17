package com.haocp.tilab.repository.Projection;

import com.haocp.tilab.enums.OrderStatus;

public interface OrderSummary {

    int getYear();
    int getMonth();
    OrderStatus getStatus();
    int getTotal();

}
