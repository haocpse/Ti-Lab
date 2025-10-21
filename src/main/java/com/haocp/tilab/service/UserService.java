package com.haocp.tilab.service;

import com.haocp.tilab.dto.request.Customer.LoginRequest;
import com.haocp.tilab.dto.request.Customer.RegisterRequest;
import com.haocp.tilab.dto.request.User.CreateUserRequest;
import com.haocp.tilab.dto.response.Customer.CustomerResponse;
import com.haocp.tilab.dto.response.Token.LoginResponse;
import com.haocp.tilab.dto.response.User.UserResponse;
import com.haocp.tilab.entity.User;

import java.util.List;

public interface UserService {

    User createUser(CreateUserRequest request);
    User createUserForLoginByEmail(CreateUserRequest request);
}
