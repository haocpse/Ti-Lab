package com.haocp.tilab.service;

import com.haocp.tilab.dto.request.User.CreateUserRequest;
import com.haocp.tilab.dto.response.User.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(CreateUserRequest request);
    List<UserResponse> getAllUsers();
    UserResponse getUserById(String id);
}
