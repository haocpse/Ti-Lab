package com.haocp.tilab.utils;

import com.haocp.tilab.entity.Staff;
import com.haocp.tilab.entity.User;
import com.haocp.tilab.exception.AppException;
import com.haocp.tilab.exception.ErrorCode;
import com.haocp.tilab.repository.StaffRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.UUID;

public final class GenerateToken {

    @Value("${jwt.signerKey}")
    static String SIGNER_KEY;

    public GenerateToken() {}

    public static String generate(String role, String username) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("haocp")
                .issueTime(new Date())
//                .expirationTime(new Date(Instant.now().plus(validDuration, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", role)
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwtObject = new JWSObject(header, payload);
        try {
            jwtObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwtObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

}
