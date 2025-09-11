package com.haocp.tilab.dto.response.User;

import com.haocp.tilab.enums.UserRole;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {

    String id;
    String username;
    String email;
    String phone;
    String password;
    UserRole role;
    String reasonBan;
    boolean active;

}
