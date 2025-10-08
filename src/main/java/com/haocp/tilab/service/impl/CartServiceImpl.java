package com.haocp.tilab.service.impl;

import com.haocp.tilab.dto.request.Cart.AddToCartRequest;
import com.haocp.tilab.dto.response.Bag.BagImgResponse;
import com.haocp.tilab.dto.response.Bag.BagResponse;
import com.haocp.tilab.dto.response.Cart.CartNumberResponse;
import com.haocp.tilab.dto.response.Cart.CartResponse;
import com.haocp.tilab.dto.response.Order.OrderResponse;
import com.haocp.tilab.entity.*;
import com.haocp.tilab.exception.AppException;
import com.haocp.tilab.exception.ErrorCode;
import com.haocp.tilab.mapper.BagMapper;
import com.haocp.tilab.mapper.CartMapper;
import com.haocp.tilab.repository.BagRepository;
import com.haocp.tilab.repository.CartRepository;
import com.haocp.tilab.repository.CustomerRepository;
import com.haocp.tilab.repository.UserRepository;
import com.haocp.tilab.service.BagImgService;
import com.haocp.tilab.service.CartService;
import com.haocp.tilab.utils.IdentifyUser;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    BagRepository bagRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CartMapper cartMapper;
    @Autowired
    BagMapper bagMapper;
    @Autowired
    BagImgService bagImgService;

    @Override
    @Transactional
    public CartResponse addToCart(AddToCartRequest request) {
        Customer customer = IdentifyUser.getCurrentCustomer(customerRepository, userRepository);
        Bag bag = bagRepository.findById(request.getBagId())
                .orElseThrow(() -> new AppException(ErrorCode.BAG_NOT_FOUND));
        Cart cart = cartRepository.findByBag_IdAndCustomer_Id(bag.getId(), customer.getId())
                .map(existingCart -> {
                    int purchaseQuantity = existingCart.getQuantity() + request.getQuantity();
                    if (bag.getQuantity() < purchaseQuantity)
                        throw new AppException(ErrorCode.EXCEED_MAXIMUM_QUANTITY, bag.getQuantity());
                    existingCart.setQuantity(purchaseQuantity);
                    existingCart.setTotalPrice(existingCart.getTotalPrice() + request.getTotalPrice());
                    return existingCart;
                })
                .orElseGet(() -> Cart.builder()
                        .bag(bag)
                        .customer(customer)
                        .quantity(request.getQuantity())
                        .totalPrice(request.getTotalPrice())
                        .build());
        cartRepository.save(cart);
        CartResponse response = cartMapper.toResponse(cart);
        response.setBagResponse(bagMapper.toResponse(bag));
        response.setUsername(customer.getUser().getUsername());
        return response;
    }

    @Override
    public Page<CartResponse> getAllCart(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Cart> carts = cartRepository.findAllWithDetails(pageable);
        return buildCartResponses(carts);
    }

    @Override
    public Page<CartResponse> getAllMyCart(int page, int size) {
        Customer customer = IdentifyUser.getCurrentCustomer(customerRepository, userRepository);
        Pageable pageable = PageRequest.of(page, size);
        Page<Cart> carts = cartRepository.findAllByCustomer_IdWithDetails(customer.getId(), pageable);
        return buildCartResponses(carts);
    }

    Page<CartResponse> buildCartResponses(Page<Cart> carts) {
        return carts.map(cart -> {
            CartResponse response = cartMapper.toResponse(cart);
            Bag bag = cart.getBag();
            response.setBagResponse(bagMapper.toResponse(bag));
            BagResponse bagResponse = response.getBagResponse();
            BagImgResponse imgResponse = bagImgService.fetchMainImage(bag.getId(), bag.getImages());
            if (imgResponse != null)
                bagResponse.setBagImages(List.of(imgResponse));
            response.setUsername(cart.getCustomer().getUser().getUsername());
            return response;
        });
    }

    @Override
    @Transactional
    public void deleteCartById(String cartId) {
        if (!cartId.isEmpty() && !cartId.isBlank())
            cartRepository.deleteById(cartId);
    }

    @Override
    public CartNumberResponse getCartNumber() {
        Customer customer = IdentifyUser.getCurrentCustomer(customerRepository, userRepository);
        int number = cartRepository.countByCustomer_Id(customer.getId());
        return CartNumberResponse.builder()
                .number(number)
                .build();
    }

}
