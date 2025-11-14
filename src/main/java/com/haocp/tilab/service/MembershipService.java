package com.haocp.tilab.service;

import com.haocp.tilab.dto.request.Coupon.UpdateCouponRequest;
import com.haocp.tilab.dto.request.Membership.CreateMembershipRequest;
import com.haocp.tilab.dto.request.Membership.UpdateMembershipRequest;
import com.haocp.tilab.dto.response.Coupon.CouponResponse;
import com.haocp.tilab.dto.response.Membership.MembershipResponse;
import com.haocp.tilab.dto.response.Membership.MyMembershipResponse;
import com.haocp.tilab.entity.Membership;

import java.util.List;

public interface MembershipService {

    MembershipResponse createMembership(CreateMembershipRequest request);
    MembershipResponse updateMembership(UpdateMembershipRequest request, Long id);
    void deleteMembership(Long id);
    MembershipResponse getMembership(Long id);
    List<MembershipResponse> getAllMembership();
    MyMembershipResponse getMyMembership();

}
