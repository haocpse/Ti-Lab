package com.haocp.tilab.dto.response.Payment;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentStatDetailResponse {

    String period;
    Double amount;
    int totalPayments;

}
