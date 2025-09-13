package com.haocp.tilab.dto.response.Token;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VerificationTokenResponse {

    boolean valid;
    String referenceId;
    Instant usedAt;

}
