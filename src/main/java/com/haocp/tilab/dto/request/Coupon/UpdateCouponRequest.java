package com.haocp.tilab.dto.request.Coupon;

import com.haocp.tilab.enums.CouponStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateCouponRequest {

    String code;
    String description;
    double discount;
    CouponStatus status;

}
