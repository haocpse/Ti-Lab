package com.haocp.tilab.dto.request.Membership;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateMembershipRequest {

    String name;
    String description;
    double min;
    double max;

}
