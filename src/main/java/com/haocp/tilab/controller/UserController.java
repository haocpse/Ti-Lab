package com.haocp.tilab.controller;

import com.haocp.tilab.dto.ApiResponse;
import com.haocp.tilab.dto.request.User.CreateUserRequest;
import com.haocp.tilab.dto.response.User.UserResponse;
import com.haocp.tilab.service.UserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUser(){
        return ApiResponse.<List<UserResponse>>builder()
                .data(userService.getAllUsers())
                .build();
    }

    @GetMapping("{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable("id") String id){
        return ApiResponse.<UserResponse>builder()
                .data(userService.getUserById(id))
                .build();
    }

    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody CreateUserRequest request){
        return ApiResponse.<UserResponse>builder()
                .data(userService.createUser(request))
                .build();
    }

}
