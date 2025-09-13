package com.haocp.tilab.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haocp.tilab.dto.ApiResponse;
import com.haocp.tilab.entity.VerificationToken;
import com.haocp.tilab.exception.ErrorCode;
import com.haocp.tilab.service.VerificationTokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorCode errorCode = ErrorCode.HAVE_NOT_LOGIN;
        Throwable cause = authException.getCause();
        if (cause instanceof JwtException jwtEx && jwtEx.getMessage().contains("expired")) {
            String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (accessToken != null && accessToken.startsWith("Bearer ")) {
                errorCode = ErrorCode.ACCESS_TOKEN_EXPIRED;
            }
        }
        response.setStatus(errorCode.getStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        ObjectMapper mapper = new ObjectMapper();

        response.getWriter().write(mapper.writeValueAsString(apiResponse));
        response.flushBuffer();

    }
}
