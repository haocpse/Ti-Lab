package com.haocp.tilab.mapper;

import com.haocp.tilab.dto.response.Payment.PaymentResponse;
import com.haocp.tilab.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentResponse toResponse(Payment payment);

}
