package com.haocp.tilab.dto.request.Order;

import com.haocp.tilab.enums.PayMethod;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateOrderRequest {

    String address;
    double subTotal;
    int feeOfDelivery;
    double total;
    String phone;
    PayMethod method;
    List<CreateOrderDetailRequest> createDetailRequests;

}
