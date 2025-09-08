package com.haocp.tilab.service;

import com.haocp.tilab.dto.request.Bag.SaveImageBagRequest;
import com.haocp.tilab.dto.response.Bag.BagImgResponse;
import com.haocp.tilab.entity.Bag;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BagImgService {

    void saveImage(Bag bag, List<MultipartFile> imageBags);
    void updateImage(Bag bag, SaveImageBagRequest request);
    List<BagImgResponse> fetchImage(Bag bag);
    BagImgResponse fetchMainImage(Bag bag);
}
