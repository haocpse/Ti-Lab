package com.haocp.tilab.repository.Projection;

public interface PaymentStatSummary {

    String getPeriod();
    Double getAmount();
    int getTotalPayments();

}
