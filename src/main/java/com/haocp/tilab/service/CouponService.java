package com.haocp.tilab.service;

import com.haocp.tilab.dto.request.Coupon.CreateCouponRequest;
import com.haocp.tilab.dto.request.Coupon.UpdateCouponRequest;
import com.haocp.tilab.dto.response.Coupon.CouponResponse;
import com.haocp.tilab.entity.Coupon;

import java.util.List;

public interface CouponService {

    CouponResponse createCoupon(CreateCouponRequest request);
    CouponResponse updateCoupon(UpdateCouponRequest request, Long id);
    void deleteCoupon(Long id);
    CouponResponse getCoupon(Long id);
    List<CouponResponse> getAllCoupon();
    List<CouponResponse> getAllAvailableCoupon();
}
