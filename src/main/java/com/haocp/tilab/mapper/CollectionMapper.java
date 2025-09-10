package com.haocp.tilab.mapper;

import com.haocp.tilab.dto.request.Collection.CreateCollectionRequest;
import com.haocp.tilab.dto.request.Collection.UpdateCollectionRequest;
import com.haocp.tilab.dto.response.Collection.CollectionResponse;
import com.haocp.tilab.entity.Collection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CollectionMapper {

    Collection toCollection(CreateCollectionRequest request);
    CollectionResponse toResponse(Collection collection);
    Collection updateToCollection(UpdateCollectionRequest request);
}
