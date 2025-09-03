package com.haocp.tilab.service.impl;

import com.haocp.tilab.dto.request.Cart.AddToCartRequest;
import com.haocp.tilab.dto.response.Cart.CartResponse;
import com.haocp.tilab.entity.Bag;
import com.haocp.tilab.entity.Cart;
import com.haocp.tilab.entity.Customer;
import com.haocp.tilab.entity.User;
import com.haocp.tilab.exception.AppException;
import com.haocp.tilab.exception.ErrorCode;
import com.haocp.tilab.mapper.BagMapper;
import com.haocp.tilab.mapper.CartMapper;
import com.haocp.tilab.repository.BagRepository;
import com.haocp.tilab.repository.CartRepository;
import com.haocp.tilab.repository.CustomerRepository;
import com.haocp.tilab.repository.UserRepository;
import com.haocp.tilab.service.CartService;
import com.haocp.tilab.utils.IdentifyUser;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public CartResponse addToCart(AddToCartRequest request) {
        Customer customer = IdentifyUser.getCurrentCustomer(customerRepository, userRepository);
        Bag bag = bagRepository.findById(request.getBagId())
                .orElseThrow(() -> new AppException(ErrorCode.BAG_NOT_FOUND));
        Cart cart = cartRepository.save(Cart.builder()
                        .bag(bag)
                        .customer(customer)
                        .quantity(request.getQuantity())
                        .totalPrice(request.getTotalPrice())
                    .build());
        CartResponse response = cartMapper.toResponse(cart);
        response.setBagResponse(bagMapper.toResponse(bag));
        response.setUsername(customer.getUser().getUsername());
        return response;
    }

    @Override
    public List<CartResponse> getAllCart() {
        return List.of();
    }

    @Override
    public List<CartResponse> getAllMyCart(String username) {
        return List.of();
    }

    @Override
    @Transactional
    public void deleteCartById(String cartId) {
        cartRepository.deleteById(cartId);
    }

}
