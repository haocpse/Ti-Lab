package com.haocp.tilab.service;

import com.haocp.tilab.dto.request.Bag.CreateBagRequest;
import com.haocp.tilab.dto.request.Bag.SaveImageBagRequest;
import com.haocp.tilab.dto.response.Bag.BagResponse;

import java.util.List;

public interface BagService {

    BagResponse createBag(CreateBagRequest createBagRequest, SaveImageBagRequest imageBagRequest);
    List<BagResponse> getAllBag();
}
