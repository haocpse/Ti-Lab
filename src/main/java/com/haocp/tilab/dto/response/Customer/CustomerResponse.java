package com.haocp.tilab.dto.response.Customer;

import com.haocp.tilab.dto.response.Membership.MembershipResponse;
import com.haocp.tilab.dto.response.User.UserResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerResponse {

    UserResponse userResponse;
    String fullName;
    double point;
    MembershipResponse membershipResponse;
    List<CustomerAddressResponse> addresses;

}
