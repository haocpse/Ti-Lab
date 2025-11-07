package com.haocp.tilab.dto.response.Order;

import com.haocp.tilab.enums.OrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderStatByStatusResponse {

    OrderStatus status;
    int total;

}
