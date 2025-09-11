package com.haocp.tilab.dto.request.Customer;

import com.haocp.tilab.enums.UserRole;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRequest {

    String username;
    String email;
    String phone;
    LocalDateTime dob;
    String firstName;
    String lastName;
    String rawPassword;
}
