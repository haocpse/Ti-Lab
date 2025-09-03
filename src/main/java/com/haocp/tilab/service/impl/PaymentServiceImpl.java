package com.haocp.tilab.service.impl;

import com.haocp.tilab.dto.request.Payment.CreatePaymentRequest;
import com.haocp.tilab.dto.response.Payment.PaymentResponse;
import com.haocp.tilab.entity.Order;
import com.haocp.tilab.entity.Payment;
import com.haocp.tilab.enums.PayMethod;
import com.haocp.tilab.enums.PaymentStatus;
import com.haocp.tilab.exception.AppException;
import com.haocp.tilab.exception.ErrorCode;
import com.haocp.tilab.mapper.PaymentMapper;
import com.haocp.tilab.repository.PaymentRepository;
import com.haocp.tilab.service.PaymentService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    PaymentMapper paymentMapper;

    @Override
    public PaymentResponse createPayment(CreatePaymentRequest request) {
        PaymentStatus status = PaymentStatus.UNPAID;
        PayMethod method = request.getMethod();
        Order order = request.getOrder();
        if(method.equals(PayMethod.CARD))
            status = PaymentStatus.PROCESSING;
        Payment payment = paymentRepository.save(Payment.builder()
                        .total(order.getTotal())
                        .order(order)
                        .method(method)
                        .status(status)
                .build());
        PaymentResponse response = paymentMapper.toResponse(payment);
        response.setPaymentId(payment.getId());
        return response;
    }

    @Override
    public PaymentResponse getPaymentByOrderId(String orderId) {
        Payment payment = paymentRepository.findPaymentByOrder_Id(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.NO_PAYMENT_SUITABLE));
        PaymentResponse response = paymentMapper.toResponse(payment);
        response.setPaymentId(payment.getId());
        return response;
    }
}
