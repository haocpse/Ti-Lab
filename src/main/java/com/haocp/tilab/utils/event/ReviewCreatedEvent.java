package com.haocp.tilab.utils.event;

import com.haocp.tilab.entity.Review;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewCreatedEvent extends ApplicationEvent {

    Review review;
    List<MultipartFile> images;

    public ReviewCreatedEvent(Object source, Review review, List<MultipartFile> images) {
        super(source);
        this.review = review;
        this.images = images;
    }

}
