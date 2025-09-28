package com.haocp.tilab.mapper;

import com.haocp.tilab.dto.response.Payment.CheckPaymentStatusResponse;
import com.haocp.tilab.dto.response.Payment.PaymentResponse;
import com.haocp.tilab.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "paymentId", source = "id")
    PaymentResponse toResponse(Payment payment);

    CheckPaymentStatusResponse toCheckResponse(Payment payment);

}
