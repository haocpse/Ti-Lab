package com.haocp.tilab.service;

import com.haocp.tilab.dto.request.Staff.CreateStaffRequest;
import com.haocp.tilab.dto.response.Staff.StaffResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StaffService {

    Page<StaffResponse> getAllStaff(int page, int size);
    StaffResponse getStaffById(String id);
    StaffResponse createStaff(CreateStaffRequest request);

}
