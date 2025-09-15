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
import com.haocp.tilab.utils.CombineToUrl;
import com.haocp.tilab.utils.SaveImage;
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
    @Autowired
    CombineToUrl combineToUrl;
    @Autowired
    SaveImage saveImage;

    @Override
    @Transactional
    public void saveImage(Bag bag, List<MultipartFile> imageBags) {
        Set<BagImg> bagImages = new HashSet<>();
        for (MultipartFile image : imageBags) {
            bagImages.add(saveBagImg(image, bag, bagImages));
        }
        bag.setImages(bagImages);
        bagRepository.save(bag);
    }

    @Override
    @Transactional
    public void updateImage(Bag bag, List<MultipartFile> imageBags, List<Long> removeIds) {
        Set<BagImg> bagImages = bag.getImages();
        bagImages.removeIf(img -> removeIds.contains(img.getId()));
        if (imageBags != null) {
            for (MultipartFile image : imageBags) {
                bagImages.add(saveBagImg(image, bag, bagImages));
            }
        }
        bagRepository.save(bag);
    }

    @Transactional
    BagImg saveBagImg(MultipartFile image, Bag bag, Set<BagImg> bagImages) {
        String imageName = image.getOriginalFilename();
        if (imageName == null || imageName.trim().isEmpty())
            throw new AppException(ErrorCode.IMG_NOT_HAVE_NAME);
        boolean main = imageName.contains("_main_");
        if(main){
            bagImages.forEach(img -> {
                String url = img.getUrl();
                if (url.contains("_main_")){
                    url = url.replace("_main_", "");
                    img.setUrl(url);
                    img.setMain(false);
                }
            });
        }
        return save(image, main, bag);
    }

    @Override
    public List<BagImgResponse> fetchImage(Bag bag) {
        return bagImgRepository.findByBag_IdOrderByMainDesc(bag.getId())
                .stream()
                .map(img -> {
                    String url = combineToUrl.bagImages(bag.getId(), img.isMain(), img.getUrl());
                    return BagImgResponse.builder()
                            .id(img.getId())
                            .url(url)
                            .build();
                })
                .toList();
    }

    @Override
    public BagImgResponse fetchMainImage(String bagId, Set<BagImg> bagImages) {
        if (bagImages != null && !bagImages.isEmpty()){
            BagImg img = bagImgRepository.findByBag_IdAndMainIsTrue(bagId)
                    .orElseThrow(() -> new AppException(ErrorCode.THERE_NO_MAIN_IMG));
            return BagImgResponse.builder()
                    .id(img.getId())
                    .url(combineToUrl.bagImages(bagId, true, img.getUrl()))
                    .build();
        }
        return null;
    }

    BagImg save(MultipartFile file, boolean main, Bag bag) {
        String imageName = saveImage.saveBagImg(file, main, bag);
        return BagImg.builder()
                .bag(bag)
                .url(imageName)
                .main(main)
                .build();

    }


}
