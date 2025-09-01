package com.haocp.tilab.service;

import com.haocp.tilab.dto.request.Coupon.CreateCouponRequest;
import com.haocp.tilab.dto.response.Coupon.CouponResponse;
import com.haocp.tilab.entity.Coupon;

public interface CouponService {

    CouponResponse createCoupon(CreateCouponRequest request);

}
