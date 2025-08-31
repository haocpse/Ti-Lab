package com.haocp.tilab.dto.request.Cart;

import com.haocp.tilab.entity.Bag;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddToCartRequest {

    String bagId;
    int quantity;
    double totalPrice;

}
