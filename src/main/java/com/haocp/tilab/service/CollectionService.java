package com.haocp.tilab.service;

import com.haocp.tilab.dto.request.Collection.CreateCollectionRequest;
import com.haocp.tilab.dto.response.Collection.CollectionResponse;
import com.haocp.tilab.entity.Collection;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CollectionService {

    CollectionResponse createCollection(CreateCollectionRequest request, MultipartFile file);
    List<CollectionResponse> getAllCollection(int page, int size);
    CollectionResponse getCollection(long id);

}