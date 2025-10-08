package com.haocp.tilab.service.impl;

import com.haocp.tilab.dto.response.Token.VerificationTokenResponse;
import com.haocp.tilab.entity.User;
import com.haocp.tilab.entity.VerificationToken;
import com.haocp.tilab.enums.TokenType;
import com.haocp.tilab.exception.AppException;
import com.haocp.tilab.exception.ErrorCode;
import com.haocp.tilab.repository.UserRepository;
import com.haocp.tilab.repository.VerificationTokenRepository;
import com.haocp.tilab.service.VerificationTokenService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class VerificationTokenServiceImpl implements VerificationTokenService {

    @Autowired
    VerificationTokenRepository verificationTokenRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public String createToken(TokenType type, User user, String referenceId) {
        String token = UUID.randomUUID().toString();
        Instant expiresAt = Instant.now();
        switch (type){
            case TokenType.REFRESH_TOKEN, TokenType.PAYMENT_PROCESSING
                    -> expiresAt = expiresAt.plus(15, ChronoUnit.MINUTES);
            case TokenType.EMAIL_VERIFY
                    -> expiresAt = expiresAt.plus(1, ChronoUnit.DAYS);
        }
        verificationTokenRepository.save(VerificationToken.builder()
                    .token(token)
                    .user(user)
                    .type(type)
                    .referenceId(referenceId)
                    .expiredAt(expiresAt)
                    .used(false)
                    .build());
        return token;
    }

    @Override
    public void createRefreshToken(String jwtId, User user, int refreshToken) {
        verificationTokenRepository.save(VerificationToken.builder()
                        .token(jwtId)
                        .user(user)
                        .type(TokenType.REFRESH_TOKEN)
                        .expiredAt(Instant.now().plusSeconds(refreshToken))
                        .used(false)
                .build());
    }

    @Override
    public VerificationTokenResponse validateToken(String token) {
        Instant now = Instant.now();
        VerificationToken verificationToken = verificationTokenRepository.findByTokenAndUsed(token, false)
                .orElseThrow(() -> new AppException(ErrorCode.TOKEN_NOT_EXIST));
        if(verificationToken.getExpiredAt().isBefore(now)) {
            throw new AppException(ErrorCode.TOKEN_EXPIRED);
        }
        verificationToken.setUsed(true);
        verificationToken.setUsedAt(now);
        verificationTokenRepository.save(verificationToken);
        return VerificationTokenResponse.builder()
                .valid(true)
                .referenceId(verificationToken.getReferenceId())
                .usedAt(verificationToken.getUsedAt())
                .build();
    }

    @Override
    public boolean isValid(String jwtId, User user) {
        VerificationToken token = verificationTokenRepository.findByTokenAndUser_IdAndUsed(jwtId, user.getId(), false)
                .orElseThrow(() -> new AppException(ErrorCode.TOKEN_NOT_EXIST));
        return token.getExpiredAt().isAfter(Instant.now());
    }

    @Override
    public String refreshToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new AppException(ErrorCode.TOKEN_NOT_EXIST));
        String newToken = UUID.randomUUID().toString();
        verificationToken.setToken(newToken);
        verificationToken.setExpiredAt(Instant.now().plusSeconds(900));
        verificationToken.setUsed(false);
        verificationTokenRepository.save(verificationToken);
        return newToken;
    }
}
