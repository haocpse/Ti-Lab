package com.haocp.tilab.service.impl;

import com.haocp.tilab.dto.request.Bag.CreateBagRequest;
import com.haocp.tilab.dto.request.Bag.SaveImageBagRequest;
import com.haocp.tilab.dto.response.Bag.BagResponse;
import com.haocp.tilab.entity.Bag;
import com.haocp.tilab.enums.BagStatus;
import com.haocp.tilab.mapper.BagMapper;
import com.haocp.tilab.repository.BagRepository;
import com.haocp.tilab.service.BagImgService;
import com.haocp.tilab.service.BagService;
import com.haocp.tilab.utils.event.BagCreatedEvent;
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
            BagResponse bagResponse = bagMapper.toResponse(bag);
            bagResponse.setBagImages(bagImgService.fetchImage(bag));
            bagResponses.add(bagResponse);
        }
        return bagResponses;
    }
}
