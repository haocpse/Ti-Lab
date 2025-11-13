package com.haocp.tilab.service.impl;

import com.haocp.tilab.dto.request.Customer.LoginRequest;
import com.haocp.tilab.dto.request.Customer.RegisterRequest;
import com.haocp.tilab.dto.request.Customer.TokenLoginGmail;
import com.haocp.tilab.dto.request.User.ChangePasswordRequest;
import com.haocp.tilab.dto.request.User.CreateUserRequest;
import com.haocp.tilab.dto.request.User.ConfirmResetRequest;
import com.haocp.tilab.dto.response.Token.LoginResponse;
import com.haocp.tilab.dto.response.Token.VerificationTokenResponse;
import com.haocp.tilab.entity.Customer;
import com.haocp.tilab.entity.Staff;
import com.haocp.tilab.entity.User;
import com.haocp.tilab.entity.VerificationToken;
import com.haocp.tilab.enums.TokenType;
import com.haocp.tilab.enums.UserRole;
import com.haocp.tilab.exception.AppException;
import com.haocp.tilab.exception.ErrorCode;
import com.haocp.tilab.mapper.UserMapper;
import com.haocp.tilab.repository.CustomerRepository;
import com.haocp.tilab.repository.MembershipRepository;
import com.haocp.tilab.repository.StaffRepository;
import com.haocp.tilab.repository.UserRepository;
import com.haocp.tilab.service.AuthService;
import com.haocp.tilab.service.UserService;
import com.haocp.tilab.service.VerificationTokenService;
import com.haocp.tilab.utils.GenerateToken;
import com.haocp.tilab.utils.event.ConfirmRegisterEvent;
import com.haocp.tilab.utils.event.PasswordResetEvent;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    StaffRepository staffRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    MembershipRepository membershipRepository;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;
    @Autowired
    GenerateToken generateToken;
    @Autowired
    ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    @Qualifier("jwtDecoderWithoutExpiration")
    JwtDecoder jwtDecoderWithoutExpiration;
    @Autowired
    VerificationTokenService verificationTokenService;
    @Value("${app.url}")
    String appUrl;
    @Autowired
    @Qualifier("googleJWTDecoder")
    JwtDecoder googleJWTDecoder;
    @Value("${app.google.issuer}")
    String googleIssuer;
    @Value("${app.raw-pass}")
    String defaultPassword;

    @Override
    @Transactional
    public void register(RegisterRequest registerRequest) {
        CreateUserRequest request = userMapper.toCreateUserRequest(registerRequest);
        request.setRole(UserRole.CUSTOMER);
        User user = userService.createUser(request);
        user.setActive(true);
        customerRepository.save(Customer.builder()
                .user(user)
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .dob(registerRequest.getDob())
                .membership(membershipRepository.findByMin(0)
                        .orElseThrow(() -> new AppException(ErrorCode.THERE_NO_MEMBERSHIP)))
                .build());
 //       String token = verificationTokenService.createToken(TokenType.EMAIL_VERIFY, user, user.getId());
 //       applicationEventPublisher.publishEvent(new ConfirmRegisterEvent(this, token, customer));
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByUsernameAndActiveIsTrue(loginRequest.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_INCORRECT));
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.PASSWORD_INCORRECT);
        }
        if(!user.isActive()){
            throw new AppException(ErrorCode.ACCOUNT_BANNED);
        }
        return LoginResponse.builder()
                .accessToken(generateToken(user, false, ""))
                .build();
    }

    @Override
    @Transactional
    public void resetPassword(ConfirmResetRequest request) {
        User user = userRepository.findByEmailAndActiveIsTrue(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_IS_WRONG));
        applicationEventPublisher.publishEvent(new PasswordResetEvent(this, user));
    }

    @Override
    public void changePassword(String id, ChangePasswordRequest request) {
        String lastPassword = request.getLastPassword();
        String newPassword = request.getNewPassword();
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
        if(!passwordEncoder.matches(lastPassword, user.getPassword())){
            throw new AppException(ErrorCode.PASSWORD_INCORRECT);
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public LoginResponse refreshToken(String expiredToken) {
        Jwt jwt = jwtDecoderWithoutExpiration.decode(expiredToken);
        String jwtId = jwt.getId();
        String username = jwt.getClaims().get("sub").toString();
        User user = userRepository.findByUsernameAndActiveIsTrue(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
        if (!verificationTokenService.isValid(jwtId, user))
            throw new AppException(ErrorCode.HAVE_NOT_LOGIN);
        return LoginResponse.builder()
                .accessToken(generateToken(user, true, jwtId))
                .build();
    }

    @Override
    public String verifyRegister(String token, String userId) {
        VerificationTokenResponse verifiedToken = verificationTokenService.validateToken(token);
        boolean valid = (verifiedToken.isValid() && userId.equals(verifiedToken.getReferenceId()));
        if(valid) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
            user.setActive(true);
            userRepository.save(user);
        }
        return appUrl + "verified" + "?status=" + valid;
    }

    @Override
    public LoginResponse loginEmail(TokenLoginGmail emailToken) {
        String token = emailToken.getToken();
        Jwt jwt = googleJWTDecoder.decode(token);
        boolean verifiedEmail = jwt.getClaim("email_verified");
        String issuer = jwt.getClaim("iss");
        if (!verifiedEmail || !issuer.equals(googleIssuer)) {
            throw new AppException(ErrorCode.INVALID_EMAIL);
        }
        String email = jwt.getClaim("email").toString();
        String lastName = jwt.getClaim("family_name").toString();
        String firstName = jwt.getClaim("given_name").toString();
        User user = userRepository.findByEmail(email)
                .orElse((createUserForFirstTime(email, firstName, lastName)));
        if (!user.isActive())
            throw new AppException(ErrorCode.ACCOUNT_BANNED);
        return LoginResponse.builder()
                .accessToken(generateToken(user, false, ""))
                .build();
    }

    User createUserForFirstTime(String email, String lastName, String firstName) {
        CreateUserRequest request = CreateUserRequest.builder()
                .username(email)
                .role(UserRole.CUSTOMER)
                .email(email)
                .rawPassword(defaultPassword)
                .build();
        User user = userService.createUserForLoginByEmail(request);
        customerRepository.save(Customer.builder()
                .user(user)
                .firstName(firstName)
                .lastName(lastName)
                .membership(membershipRepository.findByMin(0)
                        .orElseThrow(() -> new AppException(ErrorCode.THERE_NO_MEMBERSHIP)))
                .build());
        return user;
    }

    String generateToken(User user, boolean refresh, String jwtId){
        String claimJWT = user.getRole().toString();
        if (claimJWT.matches("STAFF")) {
            Staff staff = staffRepository.findById(user.getId())
                    .orElseThrow(() -> new AppException(ErrorCode.STAFF_NOT_FOUND));
            claimJWT = staff.getRole().toString();
        }
        String id = jwtId;
        if (id.isEmpty() || id.isBlank()){
             id = UUID.randomUUID().toString();
        }
        return generateToken.generateLoginToken(claimJWT, user, refresh, id);
    }
}
