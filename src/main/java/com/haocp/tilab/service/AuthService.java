package com.haocp.tilab.service;

import com.haocp.tilab.dto.request.Customer.LoginRequest;
import com.haocp.tilab.dto.request.Customer.RegisterRequest;
import com.haocp.tilab.dto.request.User.ChangePasswordRequest;
import com.haocp.tilab.dto.request.User.ConfirmResetRequest;
import com.haocp.tilab.dto.response.Token.LoginResponse;

public interface AuthService {

    void register(RegisterRequest registerRequest);
    LoginResponse login(LoginRequest loginRequest);
    void resetPassword(ConfirmResetRequest request);
    void changePassword(String id, ChangePasswordRequest request);
    LoginResponse refreshToken(String expiredToken);
    String verifyRegister(String token, String userId);
}
