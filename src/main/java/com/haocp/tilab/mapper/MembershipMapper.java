package com.haocp.tilab.mapper;

import com.haocp.tilab.dto.request.Coupon.UpdateCouponRequest;
import com.haocp.tilab.dto.request.Customer.RegisterRequest;
import com.haocp.tilab.dto.request.Membership.CreateMembershipRequest;
import com.haocp.tilab.dto.request.Membership.UpdateMembershipRequest;
import com.haocp.tilab.dto.response.Membership.MembershipResponse;
import com.haocp.tilab.entity.Coupon;
import com.haocp.tilab.entity.Membership;
import com.haocp.tilab.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface MembershipMapper {

    Membership toMembership(CreateMembershipRequest request);
    MembershipResponse toResponse(Membership membership);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Membership updateToMembership(UpdateMembershipRequest request, @MappingTarget Membership membership);

}
