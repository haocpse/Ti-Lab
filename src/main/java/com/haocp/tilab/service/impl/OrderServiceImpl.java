package com.haocp.tilab.service.impl;

import com.haocp.tilab.dto.request.Order.CreateOrderDetailRequest;
import com.haocp.tilab.dto.request.Order.CreateOrderRequest;
import com.haocp.tilab.dto.request.Payment.CreatePaymentRequest;
import com.haocp.tilab.dto.response.Customer.CustomerInOrderResponse;
import com.haocp.tilab.dto.response.Order.OrderDetailResponse;
import com.haocp.tilab.dto.response.Order.OrderResponse;
import com.haocp.tilab.dto.response.Payment.PaymentResponse;
import com.haocp.tilab.entity.*;
import com.haocp.tilab.enums.OrderStatus;
import com.haocp.tilab.enums.PayMethod;
import com.haocp.tilab.exception.AppException;
import com.haocp.tilab.exception.ErrorCode;
import com.haocp.tilab.mapper.BagMapper;
import com.haocp.tilab.mapper.CouponMapper;
import com.haocp.tilab.mapper.OrderDetailMapper;
import com.haocp.tilab.mapper.OrderMapper;
import com.haocp.tilab.repository.*;
import com.haocp.tilab.service.CartService;
import com.haocp.tilab.service.OrderService;
import com.haocp.tilab.service.PaymentService;
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
    @Autowired
    CartService cartService;
    @Autowired
    PaymentService paymentService;

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
                        .total(request.getTotal())
                        .feeOfDelivery(request.getFeeOfDelivery())
                        .coupon(coupon)
                        .customer(customer)
                        .status(OrderStatus.PREPARING)
                .build());
        PaymentResponse paymentResponse = createPayment(order, request.getMethod());
        List<OrderDetailResponse> orderDetailResponses = createOrderDetail(orderDetailRequests, order);
        OrderResponse orderResponse = orderMapper.toResponse(order);
        orderResponse.setPaymentResponse(paymentResponse);
        orderResponse.setOrderDetailResponseList(orderDetailResponses);
        return orderResponse;
    }

    List<OrderDetailResponse> createOrderDetail(List<CreateOrderDetailRequest> requests, Order order){
        List<OrderDetailResponse> responses = new ArrayList<>();
        for(CreateOrderDetailRequest request : requests){
            Bag bag = bagRepository.findById(request.getBagId())
                    .orElseThrow(() -> new AppException(ErrorCode.BAG_NOT_FOUND));
            OrderDetail orderDetail = orderDetailMapper.toOrderDetail(request);
            orderDetail.setOrder(order);
            orderDetail.setBag(bag);
            orderDetailRepository.save(orderDetail);
            deleteCartById(request.getCartId());
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
            OrderResponse response = orderMapper.toResponse(order);
            response.setOrderDetailResponseList(buildOrderDetailResponses(order.getDetails()));
            response.setPaymentResponse(paymentService.getPaymentByOrderId(order.getId()));
            responses.add(response);
        }
        return responses;
    }

    @Override
    public List<OrderResponse> getAllMyOrder(String username) {
        return List.of();
    }

    List<OrderDetailResponse> buildOrderDetailResponses(Set<OrderDetail> orderDetails) {
        List<OrderDetailResponse> responses = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetails) {
            Bag bag = orderDetail.getBag();
            OrderDetailResponse response = orderDetailMapper.toResponse(orderDetail);
            response.setBagResponse(bagMapper.toResponse(bag));
            responses.add(response);
        }
        return responses;
    }

    void deleteCartById(String cartId) {
        if (!cartId.isEmpty() && !cartId.isBlank())
            cartService.deleteCartById(cartId);
    }

    PaymentResponse createPayment(Order order, PayMethod method){
        return paymentService.createPayment(CreatePaymentRequest.builder()
                        .order(order)
                        .method(method)
                .build());
    }
}
