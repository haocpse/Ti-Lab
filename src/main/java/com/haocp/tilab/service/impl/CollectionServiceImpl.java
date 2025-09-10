package com.haocp.tilab.service.impl;

import com.haocp.tilab.dto.request.Collection.CreateCollectionRequest;
import com.haocp.tilab.dto.request.Collection.UpdateCollectionRequest;
import com.haocp.tilab.dto.response.Collection.CollectionResponse;
import com.haocp.tilab.entity.Bag;
import com.haocp.tilab.entity.Collection;
import com.haocp.tilab.exception.AppException;
import com.haocp.tilab.exception.ErrorCode;
import com.haocp.tilab.mapper.CollectionMapper;
import com.haocp.tilab.repository.BagRepository;
import com.haocp.tilab.repository.CollectionRepository;
import com.haocp.tilab.service.BagService;
import com.haocp.tilab.service.CollectionService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    CollectionRepository collectionRepository;
    @Autowired
    CollectionMapper collectionMapper;
    @Autowired
    BagService bagService;
    @Autowired
    BagRepository bagRepository;

    @Value("${app.image.url}")
    String imageUrl;

    @Override
    @Transactional
    public CollectionResponse createCollection(CreateCollectionRequest request, MultipartFile thumbnail) {
        Collection collection = collectionMapper.toCollection(request);
        collectionRepository.save(collection);
        saveThumbnailAndAddBagIntoCollection(thumbnail, collection, request.getAddBagIds());
        return buildCollectionResponse(collection);
    }

    @Override
    public Page<CollectionResponse> getAllCollection(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Collection> collections = collectionRepository.findAll(pageable);
        return collections.map(this::buildCollectionResponse);
    }

    @Override
    public CollectionResponse getCollection(long id) {
        Collection collection = collectionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COLLECTION_NOT_FOUND));
        CollectionResponse response = buildCollectionResponse(collection);
        response.setUrlThumbnail(buildThumbnailUrl(collection));
        return response;
    }

    @Override
    @Transactional
    public CollectionResponse updateCollection(UpdateCollectionRequest request, Long id, MultipartFile thumbnail) {
        Collection collection = collectionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COLLECTION_NOT_FOUND));
        collectionRepository.save(collectionMapper.updateToCollection(request));
        List<String> deleteBagIds = request.getDeleteBagIds();
        if(deleteBagIds != null && !deleteBagIds.isEmpty()) {
            for (String deleteBagId : deleteBagIds) {
                bagService.removeBagFromCollection(deleteBagId);
            }
        }
        saveThumbnailAndAddBagIntoCollection(thumbnail, collection, request.getAddBagIds());
        return buildCollectionResponse(collection);
    }

    @Override
    public void deleteCollection(long id) {
        Collection collection = collectionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COLLECTION_NOT_FOUND));
        collectionRepository.delete(collection);
    }

    @Transactional
    void saveThumbnail(MultipartFile file, String imageName, Collection collection){
        Path uploadPath = Paths.get("uploads", "collection", Long.toString(collection.getId()));
        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(imageName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            collection.setThumbnail(imageName);
            collectionRepository.save(collection);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    @Transactional
    void saveThumbnailAndAddBagIntoCollection(MultipartFile thumbnail, Collection collection, List<String> addBagIds) {
        if (thumbnail != null && !thumbnail.isEmpty()) {
            String imageName = Objects.requireNonNull(thumbnail.getOriginalFilename()).replace(" ", "-");
            saveThumbnail(thumbnail, imageName, collection);
        }
        if (addBagIds != null && !addBagIds.isEmpty()) {
            for (String addBagId : addBagIds) {
                Bag bag = bagRepository.findById(addBagId)
                        .orElseThrow(() -> new AppException(ErrorCode.BAG_NOT_FOUND));
                bag.setCollection(collection);
                bagRepository.save(bag);
            }
        }
    }

    CollectionResponse buildCollectionResponse(Collection collection) {
        CollectionResponse response = collectionMapper.toResponse(collection);
        if (collection.getThumbnail() != null && !collection.getThumbnail().isEmpty()){
            response.setUrlThumbnail(buildThumbnailUrl(collection));
            return response;
        }
        return response;
    }

    String buildThumbnailUrl(Collection collection) {
        return imageUrl + "collection/" + collection.getId();
    }
}
