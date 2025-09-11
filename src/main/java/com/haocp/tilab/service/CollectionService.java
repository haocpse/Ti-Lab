package com.haocp.tilab.service;

import com.haocp.tilab.dto.request.Collection.CreateCollectionRequest;
import com.haocp.tilab.dto.request.Collection.UpdateCollectionRequest;
import com.haocp.tilab.dto.response.Collection.CollectionResponse;
import com.haocp.tilab.entity.Collection;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CollectionService {

    CollectionResponse createCollection(CreateCollectionRequest request, MultipartFile thumbnail);
    Page<CollectionResponse> getAllCollection(int page, int size);
    CollectionResponse getCollection(long id);
    CollectionResponse updateCollection(UpdateCollectionRequest request, Long id, MultipartFile thumbnail);
    void deleteCollection(long id);

}