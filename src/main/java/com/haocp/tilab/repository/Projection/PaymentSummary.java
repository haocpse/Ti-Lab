package com.haocp.tilab.repository.Projection;

import com.haocp.tilab.enums.PayMethod;

public interface PaymentSummary {

    int getYear();
    int getMonth();
    PayMethod getMethod();
    Double getTotal();

}
