package com.haocp.tilab.service;

import com.haocp.tilab.dto.request.Bag.ArtistBagResponse;
import com.haocp.tilab.dto.request.Bag.CreateBagRequest;
import com.haocp.tilab.dto.request.Bag.SaveImageBagRequest;
import com.haocp.tilab.dto.request.Bag.UpdateBagRequest;
import com.haocp.tilab.dto.response.Bag.BagResponse;
import com.haocp.tilab.dto.response.Bag.BestSellingBagsResponse;
import com.haocp.tilab.entity.Collection;
import com.haocp.tilab.enums.BagStatus;
import com.haocp.tilab.enums.BagType;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

public interface BagService {

    BagResponse createBag(CreateBagRequest createBagRequest, List<MultipartFile> imageBags);
    Page<BagResponse> getAllBag(int page, int size, String name, Boolean available);
    Page<BagResponse> getAllAvailableBag(int page, int size);
    Page<BagResponse> getAllAvailableBagByType(BagType type, int page, int size);
    Page<ArtistBagResponse> getAllArtistBag(int page, int size);
    BagResponse getBag(String id);
    void deleteBag(String id);
    BagResponse updateBag(String id, UpdateBagRequest updateBagRequest, List<MultipartFile> imageBags);
    void removeBagFromCollection(String id);
    List<BagResponse> getBagFromCollection(Long id);


    int totalBagByTypeAndStatus(BagType type, BagStatus status);
    List<BestSellingBagsResponse> getBestSellingBags(String range);

}
