package com.haocp.tilab.dto.response.Dashboard;

import com.haocp.tilab.enums.PayMethod;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentDashboardResponse {

    int month;
    int year;
    double codTotal;
    double cardTotal;

}
