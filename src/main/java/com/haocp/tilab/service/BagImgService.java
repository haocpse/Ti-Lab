package com.haocp.tilab.service;

import com.haocp.tilab.dto.request.Bag.SaveImageBagRequest;
import com.haocp.tilab.entity.Bag;

import java.util.List;

public interface BagImgService {

    void saveImage(Bag bag, SaveImageBagRequest request);
    List<String> fetchImage(Bag bag);
}
