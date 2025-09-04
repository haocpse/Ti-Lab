package com.haocp.tilab.mapper;

import com.haocp.tilab.dto.request.Bag.CreateBagRequest;
import com.haocp.tilab.dto.request.Bag.UpdateBagRequest;
import com.haocp.tilab.dto.request.Coupon.UpdateCouponRequest;
import com.haocp.tilab.dto.response.Bag.BagResponse;
import com.haocp.tilab.entity.Bag;
import com.haocp.tilab.entity.Coupon;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface BagMapper {

    Bag toBag(CreateBagRequest request);
    BagResponse toResponse(Bag bag);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Bag updateToBag(UpdateBagRequest request, @MappingTarget Bag bag);

}
