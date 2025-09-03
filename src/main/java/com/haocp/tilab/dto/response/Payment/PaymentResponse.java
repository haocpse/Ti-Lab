package com.haocp.tilab.dto.response.Payment;

import com.haocp.tilab.enums.PayMethod;
import com.haocp.tilab.enums.PaymentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentResponse {

    String paymentId;
    double total;
    PaymentStatus status;
    PayMethod method;

}
