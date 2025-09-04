package com.haocp.tilab.service.impl;

import com.haocp.tilab.dto.request.Membership.CreateMembershipRequest;
import com.haocp.tilab.dto.request.Membership.UpdateMembershipRequest;
import com.haocp.tilab.dto.response.Membership.MembershipResponse;
import com.haocp.tilab.entity.Membership;
import com.haocp.tilab.exception.AppException;
import com.haocp.tilab.exception.ErrorCode;
import com.haocp.tilab.mapper.MembershipMapper;
import com.haocp.tilab.repository.MembershipRepository;
import com.haocp.tilab.service.MembershipService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public MembershipResponse updateMembership(UpdateMembershipRequest request, Long id) {
        Membership membership = membershipRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.THERE_NO_MEMBERSHIP));
        membership = membershipMapper.updateToMembership(request, membership);
        membershipRepository.save(membership);
        return membershipMapper.toResponse(membership);
    }

    @Override
    public void deleteMembership(Long id) {
        Membership membership = membershipRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.THERE_NO_MEMBERSHIP));
        membershipRepository.delete(membership);
    }

    @Override
    public MembershipResponse getMembership(Long id) {
        Membership membership = membershipRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.THERE_NO_MEMBERSHIP));
        return membershipMapper.toResponse(membership);
    }

    @Override
    public List<MembershipResponse> getAllMembership() {
        List<Membership> memberships = membershipRepository.findAll();
        List<MembershipResponse> responses = new ArrayList<>();
        for (Membership membership : memberships) {
            MembershipResponse membershipResponse = membershipMapper.toResponse(membership);
            responses.add(membershipResponse);
        }
        return responses;
    }
}
