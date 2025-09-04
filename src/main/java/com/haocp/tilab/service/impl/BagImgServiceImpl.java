package com.haocp.tilab.service.impl;

import com.haocp.tilab.dto.request.Bag.BagImageRequest;
import com.haocp.tilab.dto.request.Bag.SaveImageBagRequest;
import com.haocp.tilab.entity.Bag;
import com.haocp.tilab.entity.BagImg;
import com.haocp.tilab.exception.AppException;
import com.haocp.tilab.exception.ErrorCode;
import com.haocp.tilab.repository.BagImgRepository;
import com.haocp.tilab.repository.BagRepository;
import com.haocp.tilab.service.BagImgService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BagImgServiceImpl implements BagImgService {

    @Autowired
    BagImgRepository bagImgRepository;
    @Autowired
    BagRepository bagRepository;

    @Value("${app.image.url}")
    String imageUrl;

    @Override
    public void saveImage(Bag bag, SaveImageBagRequest request) {
        List<BagImageRequest> images = request.getImages();
        Set<BagImg> bagImages = new HashSet<>();
        for (BagImageRequest image : images) {
            MultipartFile file = image.getImage();
            boolean main = image.isMain();
            String imageName = file.getOriginalFilename();
            if (imageName == null)
                throw new AppException(ErrorCode.FILE_IMAGE_NULL);
            Path uploadPath = Paths.get("uploads", bag.getId(), "main");
            if (!main)
                uploadPath = Paths.get("uploads", bag.getId(), "details");
            try {
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                BagImg bagImg = bagImgRepository.save(BagImg.builder()
                                .bag(bag)
                                .url(imageName)
                                .main(main)
                        .build());
                bagImages.add(bagImg);
                Path filePath = uploadPath.resolve(imageName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
            }
        }
        bag.setImages(bagImages);
        bagRepository.save(bag);
    }

    @Override
    public List<String> fetchImage(Bag bag) {
        return bagImgRepository.findByBag_IdOrderByMainDesc(bag.getId())
                .stream()
                .map(img -> {
                    String url = img.getUrl();
                    if (img.isMain())
                        return imageUrl + bag.getId() + "/main/" + url;
                    return imageUrl + bag.getId() + "/details/" + url;
                })
                .toList();
    }
}
