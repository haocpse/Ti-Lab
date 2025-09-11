package com.haocp.tilab.mapper;

import com.haocp.tilab.dto.request.Customer.RegisterRequest;
import com.haocp.tilab.dto.request.Staff.CreateStaffRequest;
import com.haocp.tilab.dto.request.User.CreateUserRequest;
import com.haocp.tilab.dto.response.User.UserResponse;
import com.haocp.tilab.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(CreateUserRequest request);
    CreateUserRequest toCreateUserRequest(RegisterRequest request);
    CreateUserRequest toCreateUserRequest(CreateStaffRequest request);
    UserResponse toUserResponse(User user);
}
