package com.haocp.tilab.controller;

import com.haocp.tilab.dto.ApiResponse;
import com.haocp.tilab.dto.request.Membership.CreateMembershipRequest;
import com.haocp.tilab.dto.response.Membership.MembershipResponse;
import com.haocp.tilab.service.MembershipService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
