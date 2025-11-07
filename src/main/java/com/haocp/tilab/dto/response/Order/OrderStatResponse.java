package com.haocp.tilab.dto.response.Order;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderStatResponse {

    String typePeriod;
    List<OrderStatDetailResponse> details;

}
