package com.haocp.tilab.service;

import com.haocp.tilab.dto.request.Staff.CreateStaffRequest;
import com.haocp.tilab.dto.response.Staff.StaffResponse;

import java.util.List;

public interface StaffService {

    List<StaffResponse> getAllStaff();
    StaffResponse getStaffById(String id);
    StaffResponse createStaff(CreateStaffRequest request);

}
