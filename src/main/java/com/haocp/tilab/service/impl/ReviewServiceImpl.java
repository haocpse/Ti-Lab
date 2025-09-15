package com.haocp.tilab.service.impl;

import com.haocp.tilab.dto.request.Review.AddReplyRequest;
import com.haocp.tilab.dto.request.Review.CreateReviewRequest;
import com.haocp.tilab.dto.response.Review.ReviewResponse;
import com.haocp.tilab.entity.Customer;
import com.haocp.tilab.entity.Review;
import com.haocp.tilab.entity.ReviewImg;
import com.haocp.tilab.exception.AppException;
import com.haocp.tilab.exception.ErrorCode;
import com.haocp.tilab.mapper.ReviewMapper;
import com.haocp.tilab.repository.BagRepository;
import com.haocp.tilab.repository.CustomerRepository;
import com.haocp.tilab.repository.ReviewRepository;
import com.haocp.tilab.repository.UserRepository;
import com.haocp.tilab.service.ReviewService;
import com.haocp.tilab.utils.IdentifyUser;
import com.haocp.tilab.utils.event.ReviewCreatedEvent;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    ReviewMapper reviewMapper;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BagRepository bagRepository;
    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public ReviewResponse creatReview(String bagId, CreateReviewRequest request, List<MultipartFile> reviewImages) {
        Customer customer = IdentifyUser.getCurrentCustomer(customerRepository, userRepository);
        Review review = reviewMapper.toReview(request);
        review.setCustomer(customer);
        review.setBag(bagRepository.findById(bagId)
                .orElseThrow(() -> new AppException(ErrorCode.BAG_NOT_FOUND)));
        reviewRepository.save(review);
        if (reviewImages != null && !reviewImages.isEmpty()) {
            applicationEventPublisher.publishEvent(new ReviewCreatedEvent(this, review, reviewImages));
        }
        return buildReviewResponse(review);
    }

    @Override
    public void deleteReview(Long id) {

    }

    @Override
    public ReviewResponse replyReview(Long id, AddReplyRequest request) {
        return null;
    }

    ReviewResponse buildReviewResponse(Review review) {
        ReviewResponse response = reviewMapper.toResponse(review);
        response.setFullName(review.getCustomer().getFirstName() + " " + review.getCustomer().getLastName());
        return response;
    }
}
