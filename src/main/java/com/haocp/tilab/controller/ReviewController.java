package com.haocp.tilab.controller;

import com.haocp.tilab.dto.ApiResponse;
import com.haocp.tilab.dto.request.Review.CreateReviewRequest;
import com.haocp.tilab.dto.response.Review.ReviewResponse;
import com.haocp.tilab.service.ReviewService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @PostMapping(value = "/bags/{bagId}/reviews", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ReviewResponse> createReview(@RequestParam(value = "review") CreateReviewRequest request,
                                                    @RequestParam(value = "reviewImage", required = false) List<MultipartFile> reviewImages, @PathVariable String bagId) {

        return ApiResponse.<ReviewResponse>builder()
                .data(reviewService.creatReview(bagId, request, reviewImages))
                .build();
    }

}
