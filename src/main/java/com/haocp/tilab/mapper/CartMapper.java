package com.haocp.tilab.mapper;

import com.haocp.tilab.dto.request.Bag.CreateBagRequest;
import com.haocp.tilab.dto.request.Cart.AddToCartRequest;
import com.haocp.tilab.dto.response.Bag.BagResponse;
import com.haocp.tilab.dto.response.Cart.CartResponse;
import com.haocp.tilab.entity.Bag;
import com.haocp.tilab.entity.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {

    Cart toCart(AddToCartRequest request);
    CartResponse toResponse(Cart cart);

}
