package com.haocp.tilab.service;

import com.haocp.tilab.dto.request.Coupon.CreateCouponRequest;
import com.haocp.tilab.dto.response.Coupon.CouponResponse;
import com.haocp.tilab.entity.Coupon;

import java.util.List;

public interface CouponService {

    CouponResponse createCoupon(CreateCouponRequest request);
    CouponResponse updateCoupon(Coupon coupon);
    void deleteCoupon(Coupon coupon);
    List<CouponResponse> getAllCoupon();
    List<CouponResponse> getAllAvailableCoupon();
}
