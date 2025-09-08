package com.haocp.tilab.service;

import com.haocp.tilab.dto.request.Bag.ArtistBagResponse;
import com.haocp.tilab.dto.request.Bag.CreateBagRequest;
import com.haocp.tilab.dto.request.Bag.SaveImageBagRequest;
import com.haocp.tilab.dto.request.Bag.UpdateBagRequest;
import com.haocp.tilab.dto.response.Bag.BagResponse;
import com.haocp.tilab.enums.BagType;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

public interface BagService {

    BagResponse createBag(CreateBagRequest createBagRequest, List<MultipartFile> imageBags);
    Page<BagResponse> getAllBag(int page, int size);
    Page<BagResponse> getAllAvailableBag(int page, int size);
    Page<BagResponse> getAllAvailableBagByType(BagType type, int page, int size);
    Page<ArtistBagResponse> getAllArtistBag(int page, int size);
    BagResponse getBag(String id);
    void deleteBag(String id);
    BagResponse updateBag(String id, UpdateBagRequest updateBagRequest, List<MultipartFile> imageBags);
}
