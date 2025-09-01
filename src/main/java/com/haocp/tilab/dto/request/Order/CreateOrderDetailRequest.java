package com.haocp.tilab.dto.request.Order;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateOrderDetailRequest {

    String cartId;
    String bagId;
    int quantity;
    double totalPrice;

}
