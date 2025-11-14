package com.haocp.tilab.dto.response.Membership;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MyMembershipResponse {

    String name;
    double point;
    Double nextMin;

}
