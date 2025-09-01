package com.haocp.tilab.dto.response.Coupon;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CouponResponse {

    long couponId;
    String code;
    String description;
    double discount;

}
