package com.haocp.tilab.dto.response.Payment;

import com.haocp.tilab.enums.PaymentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CheckPaymentStatusResponse {

    String id;
    PaymentStatus status;
    Instant payAt;

}
