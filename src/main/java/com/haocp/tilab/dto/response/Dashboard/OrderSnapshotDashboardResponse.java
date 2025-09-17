package com.haocp.tilab.dto.response.Dashboard;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderSnapshotDashboardResponse {

    int processingOrder;
    int completedOrder;
    int failedOrder;
    int afterSalesOrder;

}
