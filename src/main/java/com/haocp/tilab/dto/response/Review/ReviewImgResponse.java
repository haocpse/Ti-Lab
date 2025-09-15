package com.haocp.tilab.dto.response.Review;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewImgResponse {

    Long id;
    String urlReviewImg;

}
