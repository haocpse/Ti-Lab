package com.haocp.tilab.dto.response.Review;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewReplyResponse {

    Long id;
    String fullName;
    String content;
    Instant createdAt;

}
