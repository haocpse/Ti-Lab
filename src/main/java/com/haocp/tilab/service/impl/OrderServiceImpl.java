package com.haocp.tilab.service.impl;

import com.haocp.tilab.dto.request.Order.CreateOrderDetailRequest;
import com.haocp.tilab.dto.request.Order.CreateOrderRequest;
import com.haocp.tilab.dto.response.Customer.CustomerInOrderResponse;
import com.haocp.tilab.dto.response.Order.OrderDetailResponse;
import com.haocp.tilab.dto.response.Order.OrderResponse;
import com.haocp.tilab.entity.*;
import com.haocp.tilab.enums.OrderStatus;
import com.haocp.tilab.exception.AppException;
import com.haocp.tilab.exception.ErrorCode;
import com.haocp.tilab.mapper.BagMapper;
import com.haocp.tilab.mapper.CouponMapper;
import com.haocp.tilab.mapper.OrderDetailMapper;
import com.haocp.tilab.mapper.OrderMapper;
import com.haocp.tilab.repository.*;
import com.haocp.tilab.service.OrderService;
import com.haocp.tilab.utils.IdentifyUser;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderDetailMapper orderDetailMapper;
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CouponRepository couponRepository;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    BagRepository bagRepository;
    @Autowired
    BagMapper bagMapper;
    @Autowired
    CouponMapper couponMapper;


    @Override
    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
        Customer customer = IdentifyUser.getCurrentCustomer(customerRepository, userRepository);
        Long couponId = request.getCouponId();
        List<CreateOrderDetailRequest> orderDetailRequests = request.getCreateDetailRequests();
        Coupon coupon = null;
        if (couponId != null) {
        coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new AppException(ErrorCode.COUPON_NOT_EXIST));
        }
        Order order = orderRepository.save(Order.builder()
                        .numberOfBag(orderDetailRequests.size())
                        .addressToDelivery(request.getAddress())
                        .subTotal(request.getSubTotal())
                        .method(request.getMethod())
                        .total(request.getTotal())
                        .feeOfDelivery(request.getFeeOfDelivery())
                        .coupon(coupon)
                        .customer(customer)
                        .status(OrderStatus.PREPARING)
                .build());
        List<OrderDetailResponse> orderDetailResponses = createOrderDetail(orderDetailRequests, order);
        OrderResponse orderResponse = buildOrderResponse(order);
        orderResponse.setOrderDetailResponseList(orderDetailResponses);
        return orderResponse;
    }

    @Transactional
    List<OrderDetailResponse> createOrderDetail(List<CreateOrderDetailRequest> requests, Order order){
        List<OrderDetailResponse> responses = new ArrayList<>();
        for(CreateOrderDetailRequest request : requests){
            Bag bag = bagRepository.findById(request.getBagId())
                    .orElseThrow(() -> new AppException(ErrorCode.BAG_NOT_FOUND));
            OrderDetail orderDetail = orderDetailMapper.toOrderDetail(request);
            orderDetail.setOrder(order);
            orderDetail.setBag(bag);
            orderDetailRepository.save(orderDetail);
            OrderDetailResponse response = orderDetailMapper.toResponse(orderDetail);
            response.setBagResponse(bagMapper.toResponse(bag));
            responses.add(response);
        }
        return responses;
    }

    @Override
    public List<OrderResponse> getAllOrder() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponse> responses = new ArrayList<>();
        for(Order order : orders){
            OrderResponse response = buildOrderResponse(order);
            response.setOrderDetailResponseList(buildOrderDetailResponses(order.getDetails()));
            responses.add(response);
        }
        return responses;
    }

    @Override
    public List<OrderResponse> getAllMyOrder(String username) {
        return List.of();
    }

    OrderResponse buildOrderResponse(Order order) {
        Customer customer = order.getCustomer();
        Coupon coupon = order.getCoupon();
        OrderResponse orderResponse = orderMapper.toResponse(order);
        orderResponse.setCouponResponse(couponMapper.toResponse(coupon));
        orderResponse.setCustomerResponse(CustomerInOrderResponse.builder()
                        .customerId(customer.getId())
                        .fullName(customer.getFirstName() + " " + customer.getLastName())
                        .phone(customer.getUser().getPhone())
                        .email(customer.getUser().getEmail())
                .build());
        return orderResponse;
    }

    List<OrderDetailResponse> buildOrderDetailResponses(Set<OrderDetail> orderDetails) {
        List<OrderDetailResponse> responses = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetails) {
            Bag bag = orderDetailRepository.findById(orderDetail.getId())
                    .orElseThrow(() -> new AppException(ErrorCode.ORDER_DETAIL_NOT_FOUND))
                    .getBag();
            OrderDetailResponse response = orderDetailMapper.toResponse(orderDetail);
            response.setBagResponse(bagMapper.toResponse(bag));
            responses.add(response);
        }
        return responses;
    }
}
