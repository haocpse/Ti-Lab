package com.haocp.tilab.service.impl;

import com.haocp.tilab.dto.request.User.CreateUserRequest;
import com.haocp.tilab.dto.response.User.UserResponse;
import com.haocp.tilab.repository.UserRepository;
import com.haocp.tilab.service.UserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        return null;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return List.of();
    }

    @Override
    public UserResponse getUserById(String id) {
        return null;
    }
}
