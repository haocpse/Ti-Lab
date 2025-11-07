package com.haocp.tilab.service.impl;

import com.haocp.tilab.dto.request.Order.CreateOrderDetailRequest;
import com.haocp.tilab.dto.request.Order.CreateOrderRequest;
import com.haocp.tilab.dto.request.Payment.CreatePaymentRequest;
import com.haocp.tilab.dto.response.Bag.BagImgResponse;
import com.haocp.tilab.dto.response.Bag.BagResponse;
import com.haocp.tilab.dto.response.Customer.CustomerInOrderResponse;
import com.haocp.tilab.dto.response.Order.*;
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
import com.haocp.tilab.repository.Projection.OrderSummary;
import com.haocp.tilab.service.BagImgService;
import com.haocp.tilab.service.CartService;
import com.haocp.tilab.service.OrderService;
import com.haocp.tilab.service.PaymentService;
import com.haocp.tilab.utils.CommonHelper;
import com.haocp.tilab.utils.IdentifyUser;
import com.haocp.tilab.utils.WeekRangeUtil;
import com.haocp.tilab.utils.event.OrderCreatedEvent;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
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
    CartService cartService;
    @Autowired
    ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    PaymentService paymentService;
    @Autowired
    BagImgService bagImgService;
    @Autowired
    CommonHelper commonHelper;

    @Override
    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
        Customer customer = IdentifyUser.getCurrentCustomer(customerRepository, userRepository);
        List<CreateOrderDetailRequest> orderDetailRequests = request.getCreateDetailRequests();
        Order order = orderRepository.save(Order.builder()
                        .numberOfBag(orderDetailRequests.size())
                        .addressToDelivery(request.getAddress())
                        .subTotal(request.getSubTotal())
                        .phone(request.getPhone())
                        .total(request.getTotal())
                        .feeOfDelivery(request.getFeeOfDelivery())
                        .customer(customer)
                        .status(OrderStatus.PREPARING)
                .build());
        Set<OrderDetailResponse> orderDetailResponses = createOrderDetail(orderDetailRequests, order);
        applicationEventPublisher.publishEvent(new OrderCreatedEvent(this,
                customer,
                order,
                request.getMethod(),
                customer.getUser().getEmail(),
                customer.getMembership().getName(),
                orderDetailResponses));
        OrderResponse orderResponse = orderMapper.toResponse(order);
        orderResponse.setOrderId(order.getId());
        orderResponse.setPaymentResponse(paymentService.getPaymentByOrderId(order.getId()));
        orderResponse.setOrderDetailResponseList(orderDetailResponses);
        return orderResponse;
    }

    @Transactional
    Set<OrderDetailResponse> createOrderDetail(List<CreateOrderDetailRequest> requests, Order order){
        Set<OrderDetailResponse> responses = new HashSet<>();
        for(CreateOrderDetailRequest request : requests){
            Bag bag = bagRepository.findById(request.getBagId())
                    .orElseThrow(() -> new AppException(ErrorCode.BAG_NOT_FOUND));
            OrderDetail orderDetail = orderDetailMapper.toOrderDetail(request);
            orderDetail.setOrder(order);
            orderDetail.setBag(bag);
            orderDetailRepository.save(orderDetail);
            if(!request.getCartId().isEmpty())
                cartService.deleteCartById(request.getCartId());
            OrderDetailResponse response = orderDetailMapper.toResponse(orderDetail);
            BagResponse bagResponse = bagMapper.toResponse(bag);
            BagImgResponse bagImgResponse = bagImgService.fetchMainImage(bag.getId(), bag.getImages());
            bagResponse.setBagImages(bagImgResponse != null ? List.of(bagImgResponse) : List.of());
            response.setBagResponse(bagResponse);
            responses.add(response);
        }
        return responses;
    }

    @Override
    public Page<OrderResponse> getAllOrder(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orders = orderRepository.findAllWithDetails(pageable);
        return buildOrderResponses(orders);
    }

    @Override
    public Page<OrderResponse> getAllMyOrder(OrderStatus status, int page, int size) {
        Customer customer = IdentifyUser.getCurrentCustomer(customerRepository, userRepository);
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orders;
        if (status != null)
            orders = orderRepository.findAllByCustomer_IdAndStatusWithDetails(customer.getId(), status, pageable);
        else
            orders = orderRepository.findAllByCustomer_IdWithDetails(customer.getId(), pageable);
        return buildMyOrderResponses(orders);
    }

    @Override
    public int totalOrderByStatus(List<OrderStatus> statuses) {
        if (statuses != null)
            return orderRepository.countOrderByStatusIn(statuses);
        else
            return (int) orderRepository.count();

    }

    @Override
    public List<OrderSummary> getOrderSummary(LocalDate from, LocalDate to) {
        return orderRepository.getOrderSummary(from, to);
    }

    @Override
    public OrderStatResponse getOrderStat(String range) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime from = commonHelper.getFromDate(range, now);
        long daysBetween = ChronoUnit.DAYS.between(from, now);
        String typePeriod;
        List<OrderStatDetailResponse> details;
        if (daysBetween <= 31) {
            details =  orderRepository.getOrderStatsByDay(from, now)
                    .stream()
                    .map(os -> OrderStatDetailResponse.builder()
                            .totalOrders(os.getTotalOrders())
                            .period(os.getPeriod())
                            .build())
                    .toList();
            typePeriod = "DAY";
        } else if (daysBetween <= 92) {
            details =  orderRepository.getOrderStatsByWeek(from, now)
                    .stream()
                    .map(os -> OrderStatDetailResponse.builder()
                            .totalOrders(os.getTotalOrders())
                            .period(WeekRangeUtil.formatYearWeek(os.getPeriod()))
                            .build())
                    .toList();
            typePeriod = "WEEK";
        } else {
            details = orderRepository.getOrderStatsByMonth(from, now)
                    .stream()
                    .map(os -> OrderStatDetailResponse.builder()
                            .totalOrders(os.getTotalOrders())
                            .period(os.getPeriod())
                            .build())
                    .toList();
            typePeriod = "MONTH";
        }
        return OrderStatResponse.builder()
                .typePeriod(typePeriod)
                .details(details)
                .build();
    }

    @Override
    public List<OrderStatByStatusResponse> getOrderStatByStatus(String range) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime from = commonHelper.getFromDate(range, now);
        return orderRepository.getOrderStatusSummary(from, now)
                .stream()
                .map(os -> OrderStatByStatusResponse.builder()
                        .status(os.getGroupedStatus())
                        .total(os.getTotal())
                        .build())
                .toList();
    }

    Page<OrderResponse> buildOrderResponses(Page<Order> orders) {
        return orders.map(order -> {
            OrderResponse response = orderMapper.toResponseWithoutCoupon(order);
            response.setOrderId(order.getId());
            response.setOrderDetailResponseList(buildOrderDetailResponses(order.getDetails()));
            return response;
        });
    }

    Page<OrderResponse> buildMyOrderResponses(Page<Order> orders) {
        return orders.map(order -> {
            OrderResponse response = orderMapper.toResponseWithoutCoupon(order);
            response.setOrderId(order.getId());
            return response;
        });
    }

    Set<OrderDetailResponse> buildOrderDetailResponses(Set<OrderDetail> orderDetails) {
        Set<OrderDetailResponse> responses = new HashSet<>();
        for (OrderDetail orderDetail : orderDetails) {
            Bag bag = orderDetail.getBag();
            OrderDetailResponse response = orderDetailMapper.toResponse(orderDetail);
            response.setBagResponse(bagMapper.toResponse(bag));
            responses.add(response);
        }
        return responses;
    }

}
