package com.haocp.tilab.dto.request.Bag;

import com.haocp.tilab.enums.BagStatus;
import com.haocp.tilab.enums.BagType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateBagRequest {

    String name;
    String description;
    String author;
    double price;
    int quantity;
    double length;
    double weight;
    BagType type;
    List<Long> removeIds;

}
