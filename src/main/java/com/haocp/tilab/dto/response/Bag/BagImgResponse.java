package com.haocp.tilab.dto.response.Bag;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BagImgResponse {

    long id;
    String url;

}
