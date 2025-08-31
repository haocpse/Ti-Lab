package com.haocp.tilab.service.impl;

import com.haocp.tilab.dto.request.Membership.CreateMembershipRequest;
import com.haocp.tilab.dto.response.Membership.MembershipResponse;
import com.haocp.tilab.entity.Membership;
import com.haocp.tilab.mapper.MembershipMapper;
import com.haocp.tilab.repository.MembershipRepository;
import com.haocp.tilab.service.MembershipService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MembershipServiceImp implements MembershipService {

    @Autowired
    MembershipRepository membershipRepository;
    @Autowired
    MembershipMapper membershipMapper;

    @Override
    public MembershipResponse createMembership(CreateMembershipRequest request) {
        Membership membership = membershipRepository.save(membershipMapper.toMembership(request));
        return membershipMapper.toResponse(membership);
    }
}
