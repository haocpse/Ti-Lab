package com.haocp.tilab.dto.response.Dashboard;

import com.haocp.tilab.enums.OrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailDashboardResponse {

    int year;
    int month;
    int processingOrder;
    int completedOrder;
    int failedOrder;
    int afterSalesOrder;

}
