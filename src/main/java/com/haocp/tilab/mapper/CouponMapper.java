package com.haocp.tilab.mapper;

import com.haocp.tilab.dto.request.Coupon.CreateCouponRequest;
import com.haocp.tilab.dto.response.Coupon.CouponResponse;
import com.haocp.tilab.entity.Coupon;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CouponMapper {

    Coupon toCoupon(CreateCouponRequest request);
    CouponResponse toResponse(Coupon coupon);
}
