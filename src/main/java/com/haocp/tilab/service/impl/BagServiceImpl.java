package com.haocp.tilab.service.impl;

import com.haocp.tilab.dto.request.Bag.ArtistBagResponse;
import com.haocp.tilab.dto.request.Bag.CreateBagRequest;
import com.haocp.tilab.dto.request.Bag.UpdateBagRequest;
import com.haocp.tilab.dto.response.Bag.BagImgResponse;
import com.haocp.tilab.dto.response.Bag.BagResponse;
import com.haocp.tilab.entity.Bag;
import com.haocp.tilab.entity.Collection;
import com.haocp.tilab.enums.BagStatus;
import com.haocp.tilab.enums.BagType;
import com.haocp.tilab.exception.AppException;
import com.haocp.tilab.exception.ErrorCode;
import com.haocp.tilab.mapper.BagMapper;
import com.haocp.tilab.repository.BagRepository;
import com.haocp.tilab.repository.CollectionRepository;
import com.haocp.tilab.service.BagImgService;
import com.haocp.tilab.service.BagService;
import com.haocp.tilab.utils.event.BagCreatedEvent;
import com.haocp.tilab.utils.event.BagUpdatedEvent;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    CollectionRepository collectionRepository;
    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public BagResponse createBag(CreateBagRequest createBagRequest, List<MultipartFile> imageBags) {
        Bag bag = bagRepository.save(create(createBagRequest));
        BagResponse bagResponse = bagMapper.toResponse(bag);
        if (imageBags != null) {
            applicationEventPublisher.publishEvent(new BagCreatedEvent(this, bag, imageBags));
        }
        bagResponse.setBagImages(bagImgService.fetchImage(bag));
        return bagResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BagResponse> getAllBag(int page, int size, String name, Boolean available) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Bag> bags;
        if (!name.isEmpty() || !name.isBlank()){
            if (available != null && available)
                bags = bagRepository.findAllByNameContainsIgnoreCaseAndStatusNot(name, BagStatus.DELETED, pageable);
            else
                bags = bagRepository.findAllByNameContainsIgnoreCase(name, pageable);
        }
        else {
            if (available != null && available)
                bags = bagRepository.findAllByStatusNot(BagStatus.DELETED, pageable);
            else
                bags = bagRepository.findAll(pageable);
        }
        return bags.map(this::buildBagResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BagResponse> getAllAvailableBag(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Bag> bags = bagRepository.findAllByStatusNot(BagStatus.DELETED, pageable);
        return bags.map(this::buildBagResponse);
    }

    @Override
    @Transactional
    public Page<BagResponse> getAllAvailableBagByType(BagType type, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Bag> bags = bagRepository.findAllByStatusNotAndType(BagStatus.DELETED, type, pageable);
        return bags.map(this::buildBagResponse);
    }

    @Override
    @Transactional
    public Page<ArtistBagResponse> getAllArtistBag(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Collection> collections = collectionRepository.findAll(pageable);
        return collections.map(collection -> {
            PageRequest pageableBag = PageRequest.of(0, 4);
            List<BagResponse> bags = bagRepository.findByCollection_Id(collection.getId(), pageableBag)
                    .stream()
                    .map(this::buildBagResponse)
                    .toList();
            return ArtistBagResponse.builder()
                    .name(collection.getName())
                    .bags(bags)
                    .build();
        });
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
    public BagResponse updateBag(String id, UpdateBagRequest updateBagRequest, List<MultipartFile> imageBags) {
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
        if (imageBags != null || !updateBagRequest.getRemoveIds().isEmpty()) {
            applicationEventPublisher.publishEvent(new BagUpdatedEvent(this, bag, imageBags, updateBagRequest.getRemoveIds()));
        }
        return response ;
    }

    @Override
    public void removeBagFromCollection(String id) {
        Bag bag = bagRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BAG_NOT_FOUND));
        bag.setCollection(null);
        bagRepository.save(bag);
    }

    @Override
    public List<BagResponse> getBagFromCollection(Long id) {
        Set<Bag> bags = bagRepository.findByCollection_Id(id);
        return bags.stream()
                .map(this::buildBagResponse)
                .toList();
    }

    Bag create(CreateBagRequest createBagRequest){
        BagStatus status = BagStatus.IN_STOCK;
        if (createBagRequest.getQuantity() <= 10){
            status = BagStatus.ALMOST_OOS;
            if (createBagRequest.getQuantity() == 0)
                status = BagStatus.OUT_OF_STOCK;
        }
        log.info(createBagRequest.toString());
        Bag bag = bagMapper.toBag(createBagRequest);
        bag.setStatus(status);
        return bag;
    }

    @Transactional
    BagResponse buildBagResponse(Bag bag){
        BagResponse bagResponse = bagMapper.toResponse(bag);
        BagImgResponse response = bagImgService.fetchMainImage(bag.getId(), bag.getImages());
        if (response != null)
            bagResponse.setBagImages(List.of(response));
        return bagResponse;
    }
}
