package com.haocp.tilab.service;

import com.haocp.tilab.dto.response.Dashboard.BagDashboardResponse;
import com.haocp.tilab.dto.response.Dashboard.OrderDetailDashboardResponse;
import com.haocp.tilab.dto.response.Dashboard.OrderSnapshotDashboardResponse;
import com.haocp.tilab.dto.response.Dashboard.PaymentDashboardResponse;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface DashboardService {

    BagDashboardResponse getBagDashboard();
    List<PaymentDashboardResponse> getPaymentDashboard(LocalDate from, LocalDate to);
    OrderSnapshotDashboardResponse getOrderSnapshotDashboard();
    List<OrderDetailDashboardResponse> getOrderDetailDashboard(LocalDate from, LocalDate to);
}
