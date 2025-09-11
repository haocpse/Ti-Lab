package com.haocp.tilab.service;

import com.haocp.tilab.dto.request.Customer.LoginRequest;
import com.haocp.tilab.dto.request.Customer.RegisterRequest;
import com.haocp.tilab.dto.request.User.ResetPasswordRequest;
import com.haocp.tilab.dto.response.Token.LoginResponse;

public interface AuthService {

    LoginResponse register(RegisterRequest registerRequest);
    LoginResponse login(LoginRequest loginRequest);
    void resetPassword(ResetPasswordRequest request);
}
