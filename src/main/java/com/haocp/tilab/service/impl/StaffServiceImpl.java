package com.haocp.tilab.service.impl;

import com.haocp.tilab.dto.request.Staff.CreateStaffRequest;
import com.haocp.tilab.dto.request.User.CreateUserRequest;
import com.haocp.tilab.dto.response.Staff.StaffResponse;
import com.haocp.tilab.entity.Staff;
import com.haocp.tilab.entity.User;
import com.haocp.tilab.enums.UserRole;
import com.haocp.tilab.exception.AppException;
import com.haocp.tilab.exception.ErrorCode;
import com.haocp.tilab.mapper.StaffMapper;
import com.haocp.tilab.mapper.UserMapper;
import com.haocp.tilab.repository.StaffRepository;
import com.haocp.tilab.service.StaffService;
import com.haocp.tilab.service.UserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StaffServiceImpl implements StaffService {

    @Autowired
    StaffRepository staffRepository;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;
    @Autowired
    StaffMapper staffMapper;

    @Override
    @Transactional
    public Page<StaffResponse> getAllStaff(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Staff> staffs = staffRepository.findAll(pageRequest);
        return staffs.map(this::buildStaffResponse);
    }

    @Override
    @Transactional
    public StaffResponse getStaffById(String id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.STAFF_NOT_FOUND));
        return buildStaffResponse(staff);
    }

    @Override
    @Transactional
    public StaffResponse createStaff(CreateStaffRequest request) {
        CreateUserRequest createUserRequest = userMapper.toCreateUserRequest(request);
        createUserRequest.setRole(UserRole.STAFF);
        User user = userService.createUser(createUserRequest);
        Staff staff = staffMapper.toStaff(request);
        staff.setUser(user);
        staffRepository.save(staff);
        return buildStaffResponse(staff);
    }

    @Transactional
    StaffResponse buildStaffResponse(Staff staff) {
        StaffResponse staffResponse = staffMapper.toStaffResponse(staff);
        staffResponse.setUserResponse(userMapper.toUserResponse(staff.getUser()));
        return staffResponse;
    }
}
