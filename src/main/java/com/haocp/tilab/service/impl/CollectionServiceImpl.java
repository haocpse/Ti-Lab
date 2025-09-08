package com.haocp.tilab.service.impl;

import com.haocp.tilab.dto.request.Collection.CreateCollectionRequest;
import com.haocp.tilab.dto.response.Collection.CollectionResponse;
import com.haocp.tilab.repository.CollectionRepository;
import com.haocp.tilab.service.CollectionService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    CollectionRepository collectionRepository;

    @Override
    public CollectionResponse createCollection(CreateCollectionRequest request, MultipartFile file) {
        return null;
    }

    @Override
    public List<CollectionResponse> getAllCollection(int page, int size) {
        return List.of();
    }

    @Override
    public CollectionResponse getCollection(long id) {
        return null;
    }
}
