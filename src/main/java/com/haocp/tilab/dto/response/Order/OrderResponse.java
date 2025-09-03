package com.haocp.tilab.dto.response.Order;

import com.haocp.tilab.dto.response.Bag.BagResponse;
import com.haocp.tilab.dto.response.Coupon.CouponResponse;
import com.haocp.tilab.dto.response.Customer.CustomerInOrderResponse;
import com.haocp.tilab.dto.response.Customer.CustomerResponse;
import com.haocp.tilab.dto.response.Payment.PaymentResponse;
import com.haocp.tilab.enums.OrderStatus;
import com.haocp.tilab.enums.PayMethod;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {

    CustomerInOrderResponse customerResponse;
    String orderId;
    int numberOfBag;
    double subTotal;
    int feeOfDelivery;
    double total;
    OrderStatus status;
    List<OrderDetailResponse> orderDetailResponseList;
    CouponResponse couponResponse;
    PaymentResponse paymentResponse;

}
