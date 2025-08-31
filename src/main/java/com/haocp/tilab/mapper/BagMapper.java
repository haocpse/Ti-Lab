package com.haocp.tilab.mapper;

import com.haocp.tilab.dto.request.Bag.CreateBagRequest;
import com.haocp.tilab.dto.response.Bag.BagResponse;
import com.haocp.tilab.entity.Bag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BagMapper {

    Bag toBag(CreateBagRequest request);
    BagResponse toResponse(Bag bag);

}
