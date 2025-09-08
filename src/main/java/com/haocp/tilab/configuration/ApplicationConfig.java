package com.haocp.tilab.configuration;

import com.haocp.tilab.entity.Staff;
import com.haocp.tilab.entity.User;
import com.haocp.tilab.enums.StaffRole;
import com.haocp.tilab.enums.UserRole;
import com.haocp.tilab.repository.StaffRepository;
import com.haocp.tilab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class ApplicationConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${app.admin.username}")
    private String username;
    @Value("${app.admin.password}")
    private String password;

    @Bean
    ApplicationRunner applicationRunner(StaffRepository staffRepository) {
        return args -> {
            if (staffRepository.findByStaffCode("ADMIN").isEmpty()){
                Staff staff = Staff.builder()
                        .user(User.builder()
                                .email("admin@gmail.com")
                                .username(username)
                                .password(passwordEncoder.encode(password))
                                .role(UserRole.STAFF)
                                .active(true)
                                .build())
                        .staffCode("ADMIN")
                        .firstName("PHU")
                        .lastName("HAO")
                        .role(StaffRole.ADMIN)
                        .build();

                staffRepository.save(staff);
            }
        };
    }

}
