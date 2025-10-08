package com.haocp.tilab.controller;

import com.haocp.tilab.dto.ApiResponse;
import com.haocp.tilab.dto.request.Customer.LoginRequest;
import com.haocp.tilab.dto.request.Customer.RegisterRequest;
import com.haocp.tilab.dto.request.Token.RefreshTokenRequest;
import com.haocp.tilab.dto.request.User.ChangePasswordRequest;
import com.haocp.tilab.dto.request.User.ConfirmResetRequest;
import com.haocp.tilab.dto.response.Token.LoginResponse;
import com.haocp.tilab.service.AuthService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ApiResponse<Void> register(@RequestBody RegisterRequest registerRequest) {
        authService.register(registerRequest);
        return ApiResponse.<Void>builder()
                .build();
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ApiResponse.<LoginResponse>builder()
                .data(authService.login(loginRequest))
                .build();
    }

    @PostMapping("/confirm-reset")
    public ApiResponse<Void> confirmReset(@RequestBody ConfirmResetRequest request) {
        authService.resetPassword(request);
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/refresh-token")
    public ApiResponse<LoginResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        return ApiResponse.<LoginResponse>builder()
                .data(authService.refreshToken(request.getToken()))
                .build();
    }

    @PutMapping("/change-password/{id}")
    public ApiResponse<Void> changePassword(@PathVariable String id, @RequestBody ChangePasswordRequest request) {
        authService.changePassword(id, request);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping("/verify")
    public ResponseEntity<Void> verifyRegister(@RequestParam String token, @RequestParam String userId) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, authService.verifyRegister(token, userId))
                .build();
    }

}
