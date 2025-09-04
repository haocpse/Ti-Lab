package com.haocp.tilab.mapper;

import com.haocp.tilab.dto.request.Coupon.CreateCouponRequest;
import com.haocp.tilab.dto.request.Coupon.UpdateCouponRequest;
import com.haocp.tilab.dto.response.Coupon.CouponResponse;
import com.haocp.tilab.entity.Coupon;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CouponMapper {

    Coupon toCoupon(CreateCouponRequest request);
    @Mapping(target = "couponId", source = "id")
    CouponResponse toResponse(Coupon coupon);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Coupon updateToCoupon(UpdateCouponRequest request, @MappingTarget Coupon coupon);

}
