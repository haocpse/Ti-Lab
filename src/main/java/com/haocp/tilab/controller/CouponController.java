package com.haocp.tilab.controller;

import com.haocp.tilab.dto.ApiResponse;
import com.haocp.tilab.dto.request.Coupon.CreateCouponRequest;
import com.haocp.tilab.dto.request.Coupon.UpdateCouponRequest;
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
    public ApiResponse<List<CouponResponse>> getAllCoupon(@RequestParam(required = false) Boolean available) {
        List<CouponResponse> responses;
        if (available != null && available)
            responses = couponService.getAllAvailableCoupon();
        else
            responses = couponService.getAllCoupon();
        return ApiResponse.<List<CouponResponse>>builder()
                .data(responses)
                .build();
    }

    @PutMapping("{id}")
    public ApiResponse<CouponResponse> updateCoupon(@RequestBody UpdateCouponRequest request, @PathVariable Long id) {
        return ApiResponse.<CouponResponse>builder()
                .data(couponService.updateCoupon(request, id))
                .build();
    }

    @GetMapping("{id}")
    public ApiResponse<CouponResponse> getCoupon(@PathVariable Long id) {
        return ApiResponse.<CouponResponse>builder()
                .data(couponService.getCoupon(id))
                .build();
    }

    @DeleteMapping("{id}")
    public ApiResponse<Void> deleteCoupon(@PathVariable Long id) {
        couponService.deleteCoupon(id);
        return ApiResponse.<Void>builder().build();
    }

}
