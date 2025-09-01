package com.haocp.tilab.dto.response.Customer;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerInOrderResponse {

    String customerId;
    String fullName;
    String phone;
    String email;

}
