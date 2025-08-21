package com.haocp.tilab.dto.request.User;

import com.haocp.tilab.enums.UserRole;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserRequest {

    String username;
    String email;
    String password;
    UserRole role;

}
