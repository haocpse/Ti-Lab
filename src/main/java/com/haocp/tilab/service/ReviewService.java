package com.haocp.tilab.service;

import com.haocp.tilab.dto.request.Review.AddReplyRequest;
import com.haocp.tilab.dto.request.Review.CreateReviewRequest;
import com.haocp.tilab.dto.response.Review.ReviewResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewService {

    ReviewResponse creatReview(String bagId, CreateReviewRequest request, List<MultipartFile> reviewImages);
    void deleteReview(Long id);
    ReviewResponse replyReview(Long id, AddReplyRequest request);

}
