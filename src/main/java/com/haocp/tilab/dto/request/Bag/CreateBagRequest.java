package com.haocp.tilab.dto.request.Bag;

import com.haocp.tilab.enums.BagStatus;
import com.haocp.tilab.enums.BagType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateBagRequest {

    @NotBlank
    String name;
    @NotBlank
    String description;
    @NotBlank
    String author;
    @Min(0)
    double price;
    @Min(0)
    int quantity;
    @Min(0)
    double length;
    @Min(0)
    double weight;
    @NotNull
    BagType type;

    Integer mainPosition;
}
