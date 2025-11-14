package com.haocp.tilab.dto.response.Payment;

import com.haocp.tilab.enums.PayMethod;
import com.haocp.tilab.enums.PaymentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentResponse {

    String paymentId;
    String fullCustomerName;
    String phone;
    double total;
    Instant paymentDate;
    PaymentStatus status;
    PayMethod method;

}
