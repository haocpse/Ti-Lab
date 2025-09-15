package com.haocp.tilab.service;

import com.haocp.tilab.entity.Review;
import com.haocp.tilab.entity.ReviewImg;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewImgService {

    void addReviewImg(Review review, List<MultipartFile> images);

}
