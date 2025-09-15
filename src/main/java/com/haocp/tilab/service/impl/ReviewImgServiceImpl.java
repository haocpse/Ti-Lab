package com.haocp.tilab.service.impl;

import com.haocp.tilab.entity.BagImg;
import com.haocp.tilab.entity.Review;
import com.haocp.tilab.entity.ReviewImg;
import com.haocp.tilab.exception.AppException;
import com.haocp.tilab.exception.ErrorCode;
import com.haocp.tilab.repository.ReviewImgRepository;
import com.haocp.tilab.service.ReviewImgService;
import com.haocp.tilab.utils.SaveImage;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewImgServiceImpl implements ReviewImgService {

    @Autowired
    ReviewImgRepository reviewImgRepository;
    @Autowired
    SaveImage saveImage;

    @Override
    public void addReviewImg(Review review, List<MultipartFile> images) {
        images.forEach(image -> {
            String fileName = saveImage.saveReviewImg(image, review);
            reviewImgRepository.save(ReviewImg.builder()
                    .url(fileName)
                    .review(review)
                    .build());

        });
    }
}
