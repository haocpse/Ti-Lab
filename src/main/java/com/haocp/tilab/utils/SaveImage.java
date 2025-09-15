package com.haocp.tilab.utils;

import com.haocp.tilab.entity.Bag;
import com.haocp.tilab.entity.Collection;
import com.haocp.tilab.entity.Review;
import com.haocp.tilab.entity.ReviewImg;
import com.haocp.tilab.enums.ImageType;
import com.haocp.tilab.exception.AppException;
import com.haocp.tilab.exception.ErrorCode;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class SaveImage {

    public String saveBagImg(MultipartFile file, boolean main, Bag bag) {
        String imageName = getFileName(file);
        Path uploadPath = buildBagImageUploadPath(main, bag.getId());
        return save(imageName, uploadPath, file);
    }

    public String saveCollectionImg(MultipartFile file, Collection collection) {
        String imageName = getFileName(file);
        Path uploadPath = buildCollectionImageUploadPath(collection.getId());
        return save(imageName, uploadPath, file);
    }

    public String saveReviewImg(MultipartFile file, Review review) {
        String imageName = getFileName(file);
        Path uploadPath = buildReviewImageUploadPath(review.getBag().getId(), review.getId());
        return save(imageName, uploadPath, file);
    }

    private String save(String imageName, Path uploadPath, MultipartFile file) {
        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(imageName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return imageName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    private String getFileName(MultipartFile file) {
        String imageName = file.getOriginalFilename();
        if (imageName == null || imageName.trim().isEmpty())
            throw new AppException(ErrorCode.IMG_NOT_HAVE_NAME);
        return imageName.replace(" ", "-");
    }

    private Path buildBagImageUploadPath(boolean main, String bagId){
        if (!main)
            return Paths.get("uploads", bagId, "details");
        return Paths.get("uploads", bagId, "main");
    }

    private Path buildCollectionImageUploadPath(Long collectionId){
        return Paths.get("uploads", "collection", Long.toString(collectionId));
    }

    private Path buildReviewImageUploadPath(String bagId, Long reviewId){
        return Paths.get("uploads", "review", bagId, Long.toString(reviewId));
    }

}
