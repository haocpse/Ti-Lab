package com.haocp.tilab.service;

import com.haocp.tilab.dto.request.Membership.CreateMembershipRequest;
import com.haocp.tilab.dto.response.Membership.MembershipResponse;
import com.haocp.tilab.entity.Membership;

public interface MembershipService {

    MembershipResponse createMembership(CreateMembershipRequest request);

}
