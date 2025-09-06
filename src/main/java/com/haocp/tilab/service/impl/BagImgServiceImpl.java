package com.haocp.tilab.service.impl;

import com.haocp.tilab.dto.request.Bag.BagImageRequest;
import com.haocp.tilab.dto.request.Bag.SaveImageBagRequest;
import com.haocp.tilab.dto.response.Bag.BagImgResponse;
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
import org.springframework.transaction.annotation.Transactional;
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
            BagImg bagImg = save(file, imageName, main, bag);
            bagImages.add(bagImg);
        }
        bag.setImages(bagImages);
        bagRepository.save(bag);
    }

    @Override
    @Transactional
    public void updateImage(Bag bag, SaveImageBagRequest request) {
        List<BagImageRequest> images = request.getImages();
        Set<BagImg> bagImages = bagImgRepository.findByBag_Id(bag.getId());
        List<Long> ids = bagImages.stream()
                .map(BagImg::getId)
                .toList();
        for (Long id : ids) {
            BagImg img = getBagImg(id);
            bagImages.remove(img);
            deleteBagImg(img);
        }
        for (BagImageRequest image : images) {
            Long id = image.getImgId();
            MultipartFile file = image.getImage();
            boolean main = image.isMain();
            String imageName = file.getOriginalFilename();
            if (id == null){
                BagImg bagImg = save(file, imageName, main, bag);
                bagImages.add(bagImg);
            } else {
                BagImg img = bagImgRepository.findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.IMG_NOT_FOUND));
                update(file, imageName, main, bag, img);
            }
        }
        bag.setImages(bagImages);
        bagRepository.save(bag);
    }

    @Override
    public List<BagImgResponse> fetchImage(Bag bag) {
        return bagImgRepository.findByBag_IdOrderByMainDesc(bag.getId())
                .stream()
                .map(img -> {
                    String baseUrl = img.getUrl();
                    String url;
                    if (img.isMain())
                        url = imageUrl + bag.getId() + "/main/" + baseUrl;
                    else
                        url = imageUrl + bag.getId() + "/details/" + baseUrl;
                    return BagImgResponse.builder()
                            .id(img.getId())
                            .url(url)
                            .build();
                })
                .toList();
    }

    @Override
    public BagImgResponse fetchMainImage(Bag bag) {
        if (bag.getImages() != null && !bag.getImages().isEmpty()){
            BagImg img = bagImgRepository.findByBag_IdAndMainIsTrue(bag.getId())
                    .orElseThrow(() -> new AppException(ErrorCode.THERE_NO_MAIN_IMG));
            return BagImgResponse.builder()
                    .id(img.getId())
                    .url(imageUrl + bag.getId() + "/main/" + img.getUrl())
                    .build();
        }
        return null;
    }

    BagImg save(MultipartFile file, String imageName, boolean main, Bag bag) {
        Path uploadPath = builduploadPath(bag, imageName, main);
        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(imageName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return bagImgRepository.save(BagImg.builder()
                    .bag(bag)
                    .url(imageName)
                    .main(main)
                    .build());
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    void update(MultipartFile file, String imageName, boolean main, Bag bag, BagImg img) {
        Path uploadPath = builduploadPath(bag, imageName, main);
        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(imageName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            img.setUrl(imageName);
            img.setMain(main);
            bagImgRepository.save(img);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    Path builduploadPath(Bag bag, String imageName, boolean main) {
        if (imageName == null)
            throw new AppException(ErrorCode.FILE_IMAGE_NULL);
        if (!main)
            return Paths.get("uploads", bag.getId(), "details");
        return Paths.get("uploads", bag.getId(), "main");
    }

    BagImg getBagImg(Long id){
        return bagImgRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.IMG_NOT_FOUND));
    }

    void deleteBagImg(BagImg img){
        bagImgRepository.delete(img);
    }
}
