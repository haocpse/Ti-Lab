package com.haocp.tilab.service.impl;

import com.haocp.tilab.dto.request.Staff.CreateStaffRequest;
import com.haocp.tilab.dto.response.Staff.StaffResponse;
import com.haocp.tilab.repository.StaffRepository;
import com.haocp.tilab.service.StaffService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StaffServiceImpl implements StaffService {

    @Autowired
    StaffRepository staffRepository;

    @Override
    public List<StaffResponse> getAllStaff() {
        return null;
    }

    @Override
    public StaffResponse getStaffById(String id) {
        return null;
    }

    @Override
    public StaffResponse createStaff(CreateStaffRequest request) {
        return null;
    }
}
