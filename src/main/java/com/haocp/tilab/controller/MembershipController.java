package com.haocp.tilab.controller;

import com.haocp.tilab.dto.ApiResponse;
import com.haocp.tilab.dto.request.Coupon.UpdateCouponRequest;
import com.haocp.tilab.dto.request.Membership.CreateMembershipRequest;
import com.haocp.tilab.dto.request.Membership.UpdateMembershipRequest;
import com.haocp.tilab.dto.response.Coupon.CouponResponse;
import com.haocp.tilab.dto.response.Membership.MembershipResponse;
import com.haocp.tilab.service.MembershipService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memberships")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MembershipController {

    @Autowired
    MembershipService membershipService;

    @PostMapping
    public ApiResponse<MembershipResponse> createMembership(@RequestBody CreateMembershipRequest request) {
        return ApiResponse.<MembershipResponse>builder()
                .data(membershipService.createMembership(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<MembershipResponse>> getAllMembership() {
        return ApiResponse.<List<MembershipResponse>>builder()
                .data(membershipService.getAllMembership())
                .build();
    }

    @GetMapping("{id}")
    public ApiResponse<MembershipResponse> getMembership(@PathVariable Long id) {
        return ApiResponse.<MembershipResponse>builder()
                .data(membershipService.getMembership(id))
                .build();
    }

    @DeleteMapping("{id}")
    public ApiResponse<Void> deleteMembership(@PathVariable Long id) {
        membershipService.deleteMembership(id);
        return ApiResponse.<Void>builder().build();
    }

    @PutMapping("{id}")
    public ApiResponse<MembershipResponse> updateMembership(@RequestBody UpdateMembershipRequest request, @PathVariable Long id) {
        return ApiResponse.<MembershipResponse>builder()
                .data(membershipService.updateMembership(request, id))
                .build();
    }

}
