package com.haocp.tilab.dto.response.Cart;

import com.haocp.tilab.dto.response.Bag.BagResponse;
import com.haocp.tilab.dto.response.Customer.CustomerResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartResponse {

    BagResponse bagResponse;
    String username;
    int quantity;
    double totalPrice;

}
