package com.haocp.tilab.service.impl;

import com.haocp.tilab.dto.request.Customer.LoginRequest;
import com.haocp.tilab.dto.request.Customer.RegisterRequest;
import com.haocp.tilab.dto.request.User.CreateUserRequest;
import com.haocp.tilab.dto.response.Token.LoginResponse;
import com.haocp.tilab.dto.response.User.UserResponse;
import com.haocp.tilab.entity.Customer;
import com.haocp.tilab.entity.Staff;
import com.haocp.tilab.entity.User;
import com.haocp.tilab.enums.UserRole;
import com.haocp.tilab.exception.AppException;
import com.haocp.tilab.exception.ErrorCode;
import com.haocp.tilab.mapper.UserMapper;
import com.haocp.tilab.repository.CustomerRepository;
import com.haocp.tilab.repository.MembershipRepository;
import com.haocp.tilab.repository.StaffRepository;
import com.haocp.tilab.repository.UserRepository;
import com.haocp.tilab.service.UserService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    StaffRepository staffRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Value("${jwt.signerKey}")
    String SIGNER_KEY;
    @Autowired
    MembershipRepository membershipRepository;
    @Autowired
    UserMapper userMapper;

    @Override
    @Transactional
    public LoginResponse register(RegisterRequest registerRequest) {
        User user = userMapper.toUser(registerRequest);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(UserRole.CUSTOMER);
        user.setActive(true);
        Customer customer = customerRepository.save(Customer.builder()
                        .user(user)
                        .firstName(registerRequest.getFirstName())
                        .lastName(registerRequest.getLastName())
                        .membership(membershipRepository.findByMin(0)
                                .orElseThrow(() -> new AppException(ErrorCode.THERE_NO_MEMBERSHIP)))
                .build());
        return LoginResponse.builder()
                .accessToken(generateToken(customer.getUser()))
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_INCORRECT));
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.PASSWORD_INCORRECT);
        }
        if(!user.isActive()){
            throw new AppException(ErrorCode.ACCOUNT_BANNED);
        }
        return LoginResponse.builder()
                .accessToken(generateToken(user))
                .build();
    }

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        return null;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return List.of();
    }

    @Override
    public UserResponse getUserById(String id) {
        return null;
    }

    String generateToken(User user){
        String claimJWT = user.getRole().toString();
        if (claimJWT.matches("STAFF")) {
            Staff staff = staffRepository.findById(user.getId())
                    .orElseThrow(() -> new AppException(ErrorCode.STAFF_NOT_FOUND));
            claimJWT = claimJWT + "_" + staff.getRole().toString();
        }
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("haocp")
                .issueTime(new Date())
//                .expirationTime(new Date(Instant.now().plus(validDuration, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", claimJWT)
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
