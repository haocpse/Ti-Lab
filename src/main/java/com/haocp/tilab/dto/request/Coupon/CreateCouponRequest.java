package com.haocp.tilab.dto.request.Coupon;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateCouponRequest {

    String code;
    String description;
    double discount;

}
