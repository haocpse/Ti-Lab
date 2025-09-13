package com.haocp.tilab.service.impl;

import com.haocp.tilab.dto.response.Token.VerificationTokenResponse;
import com.haocp.tilab.entity.User;
import com.haocp.tilab.entity.VerificationToken;
import com.haocp.tilab.enums.TokenType;
import com.haocp.tilab.exception.AppException;
import com.haocp.tilab.exception.ErrorCode;
import com.haocp.tilab.repository.VerificationTokenRepository;
import com.haocp.tilab.service.VerificationTokenService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class VerificationTokenServiceImpl implements VerificationTokenService {

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Override
    public String createToken(TokenType type, User user, String referenceId) {
        String token = UUID.randomUUID().toString();
        verificationTokenRepository.save(VerificationToken.builder()
                .token(token)
                .user(user)
                .type(type)
                .referenceId(referenceId)
                .expiredAt(Instant.now().plusSeconds(900))
                .used(false)
                .build());
        return token;
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
}
