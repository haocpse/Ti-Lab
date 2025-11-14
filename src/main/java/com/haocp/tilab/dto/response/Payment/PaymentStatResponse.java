package com.haocp.tilab.dto.response.Payment;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentStatResponse {

    String typeRange;
    List<PaymentStatDetailResponse> details;

}
