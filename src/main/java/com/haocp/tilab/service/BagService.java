package com.haocp.tilab.service;

import com.haocp.tilab.dto.request.Bag.CreateBagRequest;
import com.haocp.tilab.dto.request.Bag.SaveImageBagRequest;
import com.haocp.tilab.dto.request.Bag.UpdateBagRequest;
import com.haocp.tilab.dto.response.Bag.BagResponse;

import java.util.List;

public interface BagService {

    BagResponse createBag(CreateBagRequest createBagRequest, SaveImageBagRequest imageBagRequest);
    List<BagResponse> getAllBag();
    List<BagResponse> getAllAvailableBag();
    BagResponse getBag(String id);
    void deleteBag(String id);
    BagResponse updateBag(String id, UpdateBagRequest updateBagRequest, SaveImageBagRequest imageBagRequest);
}
