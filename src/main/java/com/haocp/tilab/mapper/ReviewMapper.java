package com.haocp.tilab.mapper;

import com.haocp.tilab.dto.request.Review.CreateReviewRequest;
import com.haocp.tilab.dto.response.Review.ReviewResponse;
import com.haocp.tilab.entity.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    Review toReview(CreateReviewRequest request);
    ReviewResponse toResponse(Review review);

}
