package com.haocp.tilab.dto.response.Report;

import com.haocp.tilab.dto.response.Customer.CustomerResponse;
import com.haocp.tilab.dto.response.Order.OrderResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportResponse {

    OrderResponse order;
    CustomerResponse customer;
    String reason;

}
