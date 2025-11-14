package com.haocp.tilab.repository.Projection;

import com.haocp.tilab.enums.PayMethod;

public interface PaymentMethodSummary {

    PayMethod getMethod();
    int getTotal();
    Double getPercentage();

}
