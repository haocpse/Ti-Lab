package com.haocp.tilab.service;

import com.haocp.tilab.dto.response.Token.VerificationTokenResponse;
import com.haocp.tilab.entity.User;
import com.haocp.tilab.entity.VerificationToken;
import com.haocp.tilab.enums.TokenType;

public interface VerificationTokenService {

    String createToken(TokenType type, User user, String referenceId);
    VerificationTokenResponse validateToken(String token);
    String refreshToken(String token);
}
