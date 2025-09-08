package com.haocp.tilab.dto.response.Payment;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QRPaymentResponse {

    String urlQR;

}
