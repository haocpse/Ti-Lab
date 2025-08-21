package com.haocp.tilab.dto.response.Staff;

import com.haocp.tilab.dto.response.User.UserResponse;
import com.haocp.tilab.enums.StaffRole;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StaffResponse {

    UserResponse userResponse;
    String staffCode;
    String fullName;
    String businessEmail;
    StaffRole role;


}
