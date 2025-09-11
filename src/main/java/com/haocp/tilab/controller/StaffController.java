package com.haocp.tilab.controller;

import com.haocp.tilab.dto.ApiResponse;
import com.haocp.tilab.dto.request.Customer.RegisterRequest;
import com.haocp.tilab.dto.request.Staff.CreateStaffRequest;
import com.haocp.tilab.dto.response.Customer.CustomerResponse;
import com.haocp.tilab.dto.response.Staff.StaffResponse;
import com.haocp.tilab.service.StaffService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staffs")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StaffController {

    @Autowired
    StaffService staffService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<StaffResponse> createStaff(@RequestBody CreateStaffRequest request) {
        return ApiResponse.<StaffResponse>builder()
                .data(staffService.createStaff(request))
                .build();
    }

    @GetMapping
    public ApiResponse<Page<StaffResponse>> getAllStaff(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<Page<StaffResponse>>builder()
                .data(staffService.getAllStaff(page, size))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<StaffResponse> getStaffById(@PathVariable("id") String id) {
        return ApiResponse.<StaffResponse>builder()
                .data(staffService.getStaffById(id))
                .build();
    }
}
