package com.haocp.tilab.mapper;

import com.haocp.tilab.dto.request.Collection.CreateCollectionRequest;
import com.haocp.tilab.dto.request.Collection.UpdateCollectionRequest;
import com.haocp.tilab.dto.response.Collection.CollectionResponse;
import com.haocp.tilab.entity.Collection;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CollectionMapper {

    Collection toCollection(CreateCollectionRequest request);
    CollectionResponse toResponse(Collection collection);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Collection updateToCollection(UpdateCollectionRequest request, @MappingTarget Collection collection);
}
