package com.haocp.tilab.dto.request.Staff;

import com.haocp.tilab.enums.StaffRole;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateStaffRequest {

    String staffCode;
    String firstName;
    String lastName;
    String username;
    String email;
    String phone;
    String rawPassword;
    StaffRole staffRole;

}
