package com.haocp.tilab.dto.request.Customer;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginRequest {

    String username;
    String password;

}
