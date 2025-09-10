package com.haocp.tilab.mapper;

import com.haocp.tilab.dto.request.Staff.CreateStaffRequest;
import com.haocp.tilab.dto.response.Staff.StaffResponse;
import com.haocp.tilab.entity.Staff;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StaffMapper {

    @Mapping(source = "email", target = "businessEmail")
    @Mapping(source = "staffRole", target = "role")
    Staff toStaff(CreateStaffRequest request);

    StaffResponse toStaffResponse(Staff staff);
}
