package com.haocp.tilab.dto.request.Payment;

import com.haocp.tilab.entity.Order;
import com.haocp.tilab.enums.PayMethod;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreatePaymentRequest {

    Order order;
    PayMethod method;

}
