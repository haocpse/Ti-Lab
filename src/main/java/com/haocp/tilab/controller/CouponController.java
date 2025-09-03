package com.haocp.tilab.controller;

import com.haocp.tilab.dto.ApiResponse;
import com.haocp.tilab.dto.request.Coupon.CreateCouponRequest;
import com.haocp.tilab.dto.request.Membership.CreateMembershipRequest;
import com.haocp.tilab.dto.response.Coupon.CouponResponse;
import com.haocp.tilab.dto.response.Membership.MembershipResponse;
import com.haocp.tilab.service.CouponService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CouponController {

    @Autowired
    CouponService couponService;

    @PostMapping
    public ApiResponse<CouponResponse> createCoupon(@RequestBody CreateCouponRequest request) {
        return ApiResponse.<CouponResponse>builder()
                .data(couponService.createCoupon(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<CouponResponse>> getAllCoupon() {
        return ApiResponse.<List<CouponResponse>>builder()
                .data(couponService.getAllCoupon())
                .build();
    }

    @GetMapping
    public ApiResponse<List<CouponResponse>> getAllAvailableCoupon() {
        return ApiResponse.<List<CouponResponse>>builder()
                .data(couponService.getAllAvailableCoupon())
                .build();
    }

}
