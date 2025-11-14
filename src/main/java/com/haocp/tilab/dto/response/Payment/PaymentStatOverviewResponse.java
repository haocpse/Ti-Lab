package com.haocp.tilab.dto.response.Payment;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentStatOverviewResponse {

    long total;
    double totalPrice;
    double avgPrice;

}
