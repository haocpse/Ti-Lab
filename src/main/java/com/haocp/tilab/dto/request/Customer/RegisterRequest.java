package com.haocp.tilab.dto.request.Customer;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRequest {

    String username;
    String email;
    String phone;
    String firstName;
    String lastName;
    String rawPassword;

}
