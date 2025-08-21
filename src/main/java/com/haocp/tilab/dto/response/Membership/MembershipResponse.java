package com.haocp.tilab.dto.response.Membership;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MembershipResponse {

    Long id;
    String name;
    String description;
    double min;
    double max;

}
