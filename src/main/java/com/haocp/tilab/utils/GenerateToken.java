package com.haocp.tilab.utils;

import com.haocp.tilab.entity.Staff;
import com.haocp.tilab.entity.User;
import com.haocp.tilab.enums.UserRole;
import com.haocp.tilab.exception.AppException;
import com.haocp.tilab.exception.ErrorCode;
import com.haocp.tilab.repository.StaffRepository;
import com.haocp.tilab.utils.event.GenerateTokenLoginEvent;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Component
public class GenerateToken {

    @Value("${jwt.signerKey}")
    private String SIGNER_KEY;
    @Value("${app.access-duration}")
    private int ACCESS_DURATION;
    @Value("${app.staff-refresh-duration}")
    private int STAFF_REFRESH_DURATION;
    @Value("${app.customer-refresh-duration}")
    private int CUSTOMER_REFRESH_DURATION;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public String generateLoginToken(String role, User user, boolean refresh, String id) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("haocp")
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plusSeconds(ACCESS_DURATION)))
                .jwtID(id)
                .claim("scope", role)
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwtObject = new JWSObject(header, payload);
        try {
            jwtObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            int refreshDuration = role.equals(UserRole.CUSTOMER.toString())
                    ? CUSTOMER_REFRESH_DURATION
                    : STAFF_REFRESH_DURATION;
            if (!refresh) {
                applicationEventPublisher.publishEvent(new GenerateTokenLoginEvent(this, id, refreshDuration, user));
            }
            return jwtObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

}
