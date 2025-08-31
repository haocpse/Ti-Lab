package com.haocp.tilab.mapper;

import com.haocp.tilab.dto.request.Customer.RegisterRequest;
import com.haocp.tilab.dto.request.Membership.CreateMembershipRequest;
import com.haocp.tilab.dto.response.Membership.MembershipResponse;
import com.haocp.tilab.entity.Membership;
import com.haocp.tilab.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MembershipMapper {

    Membership toMembership(CreateMembershipRequest request);
    MembershipResponse toResponse(Membership membership);

}
