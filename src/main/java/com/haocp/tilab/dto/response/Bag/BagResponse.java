package com.haocp.tilab.dto.response.Bag;

import com.haocp.tilab.enums.BagStatus;
import com.haocp.tilab.enums.BagType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BagResponse {

    String id;
    String name;
    double price;
    String description;
    String author;
    int quantity;
    double length;
    double weight;
    BagType type;
    BagStatus status;

}
