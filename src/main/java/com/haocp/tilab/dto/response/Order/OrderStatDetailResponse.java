package com.haocp.tilab.dto.response.Order;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderStatDetailResponse {

    String period;
    int totalOrders;

}
