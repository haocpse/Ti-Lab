package com.haocp.tilab.service;

import com.haocp.tilab.dto.request.Bag.SaveImageBagRequest;
import com.haocp.tilab.dto.response.Bag.BagImgResponse;
import com.haocp.tilab.entity.Bag;
import com.haocp.tilab.entity.BagImg;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface BagImgService {

    void saveImage(Bag bag, List<MultipartFile> imageBags);
    void updateImage(Bag bag, List<MultipartFile> imageBags, List<Long> removeIds);
    List<BagImgResponse> fetchImage(Bag bag);
    BagImgResponse fetchMainImage(String bagId, Set<BagImg> bagImages);
}
