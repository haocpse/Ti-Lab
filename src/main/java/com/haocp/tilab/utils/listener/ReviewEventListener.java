package com.haocp.tilab.utils.listener;

import com.haocp.tilab.service.ReviewImgService;
import com.haocp.tilab.utils.event.OrderCreatedEvent;
import com.haocp.tilab.utils.event.ReviewCreatedEvent;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewEventListener {

    ReviewImgService reviewImgService;

    public ReviewEventListener(ReviewImgService reviewImgService) {
        this.reviewImgService = reviewImgService;
    }

    @EventListener
    public void handleReviewCreatedEvent(ReviewCreatedEvent event) {
        reviewImgService.addReviewImg(event.getReview(), event.getImages());
    }

}
