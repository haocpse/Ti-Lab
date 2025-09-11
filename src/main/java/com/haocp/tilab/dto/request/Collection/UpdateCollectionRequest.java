package com.haocp.tilab.dto.request.Collection;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateCollectionRequest {

    String name;
    List<String> addBagIds;
    List<String> deleteBagIds;

}
