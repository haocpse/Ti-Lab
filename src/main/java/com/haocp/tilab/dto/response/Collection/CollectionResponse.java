package com.haocp.tilab.dto.response.Collection;

import com.haocp.tilab.dto.response.Bag.BagResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CollectionResponse {

    long id;
    String name;
    String urlThumbnail;
    List<BagResponse> bags;

}
