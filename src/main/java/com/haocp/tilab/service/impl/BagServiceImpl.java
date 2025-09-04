package com.haocp.tilab.service.impl;

import com.haocp.tilab.dto.request.Bag.CreateBagRequest;
import com.haocp.tilab.dto.request.Bag.SaveImageBagRequest;
import com.haocp.tilab.dto.request.Bag.UpdateBagRequest;
import com.haocp.tilab.dto.response.Bag.BagResponse;
import com.haocp.tilab.entity.Bag;
import com.haocp.tilab.enums.BagStatus;
import com.haocp.tilab.exception.AppException;
import com.haocp.tilab.exception.ErrorCode;
import com.haocp.tilab.mapper.BagMapper;
import com.haocp.tilab.repository.BagRepository;
import com.haocp.tilab.service.BagImgService;
import com.haocp.tilab.service.BagService;
import com.haocp.tilab.utils.event.BagCreatedEvent;
import com.haocp.tilab.utils.event.BagUpdatedEvent;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BagServiceImpl implements BagService {

    @Autowired
    BagRepository bagRepository;
    @Autowired
    BagImgService bagImgService;
    @Autowired
    BagMapper bagMapper;
    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public BagResponse createBag(CreateBagRequest createBagRequest, SaveImageBagRequest imageBagRequest) {
        BagStatus status = BagStatus.IN_STOCK;
        if (createBagRequest.getQuantity() <= 10){
            status = BagStatus.ALMOST_OOS;
            if (createBagRequest.getQuantity() == 0)
                status = BagStatus.OUT_OF_STOCK;
        }
        Bag bag = bagMapper.toBag(createBagRequest);
        bag.setStatus(status);
        bag = bagRepository.save(bag);
        BagResponse bagResponse = bagMapper.toResponse(bag);
        if (imageBagRequest != null) {
            applicationEventPublisher.publishEvent(new BagCreatedEvent(this, bag, imageBagRequest));
        }
        bagResponse.setBagImages(bagImgService.fetchImage(bag));
        return bagResponse;
    }

    @Override
    public List<BagResponse> getAllBag() {
        List<Bag> bags = bagRepository.findAll();
        List<BagResponse> bagResponses = new ArrayList<>();
        for(Bag bag : bags){
            bagResponses.add(buildBagResponse(bag));
        }
        return bagResponses;
    }

    @Override
    public List<BagResponse> getAllAvailableBag() {
        List<Bag> bags = bagRepository.findAllByStatusNot(BagStatus.DELETED);
        List<BagResponse> bagResponses = new ArrayList<>();
        for(Bag bag : bags){
            bagResponses.add(buildBagResponse(bag));
        }
        return bagResponses;
    }

    @Override
    public BagResponse getBag(String id) {
        Bag bag = bagRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BAG_NOT_FOUND));
        BagResponse bagResponse = bagMapper.toResponse(bag);
        bagResponse.setBagImages(bagImgService.fetchImage(bag));
        return bagResponse;
    }

    @Override
    public void deleteBag(String id) {
        Bag bag = bagRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BAG_NOT_FOUND));
        bag.setStatus(BagStatus.DELETED);
        bagRepository.save(bag);
    }

    @Override
    @Transactional
    public BagResponse updateBag(String id, UpdateBagRequest updateBagRequest, SaveImageBagRequest imageBagRequest) {
        Bag bag = bagRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BAG_NOT_FOUND));
        bag = bagMapper.updateToBag(updateBagRequest, bag);
        if (bag.getQuantity() <= 10){
            bag.setStatus(BagStatus.ALMOST_OOS);
            if (bag.getQuantity() == 0)
                bag.setStatus(BagStatus.OUT_OF_STOCK);
        } else {
            bag.setStatus(BagStatus.IN_STOCK);
        }
        BagResponse response = buildBagResponse(bagRepository.save(bag));
        if (imageBagRequest != null) {
            applicationEventPublisher.publishEvent(new BagUpdatedEvent(this, bag, imageBagRequest));
        }
        return response ;
    }

    BagResponse buildBagResponse(Bag bag){
        BagResponse bagResponse = bagMapper.toResponse(bag);
        bagResponse.setBagImages(List.of(bagImgService.fetchMainImage(bag)));
        return bagResponse;
    }
}
