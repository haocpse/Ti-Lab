package com.haocp.tilab.dto.response.Customer;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerAddressResponse {

    String address;
    String city;
    boolean isDefaultShipping;

}
