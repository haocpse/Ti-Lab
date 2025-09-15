package com.haocp.tilab.dto.response.Review;

import com.haocp.tilab.entity.Customer;
import com.haocp.tilab.entity.ReviewImg;
import com.haocp.tilab.entity.ReviewReply;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewResponse {

    Long id;
    String content;
    double score;
    Instant createAt;
    String fullName;
    Set<ReviewImgResponse> reviewImages;
    Set<ReviewReplyResponse> replies;

}
