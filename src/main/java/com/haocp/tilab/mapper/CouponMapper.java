package com.haocp.tilab.mapper;

import com.haocp.tilab.dto.request.Coupon.CreateCouponRequest;
import com.haocp.tilab.dto.response.Coupon.CouponResponse;
import com.haocp.tilab.entity.Coupon;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CouponMapper {

    Coupon toCoupon(CreateCouponRequest request);

    @Mapping(target = "couponId", source = "id")
    CouponResponse toResponse(Coupon coupon);
}
