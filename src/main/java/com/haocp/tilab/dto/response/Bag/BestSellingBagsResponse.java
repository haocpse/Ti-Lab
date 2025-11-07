package com.haocp.tilab.dto.response.Bag;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BestSellingBagsResponse {

    long total;
    String bagId;
    String bagName;
    String urlMain;

}
