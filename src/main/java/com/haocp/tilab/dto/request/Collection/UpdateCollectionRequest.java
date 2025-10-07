package com.haocp.tilab.dto.request.Collection;

import com.haocp.tilab.enums.CollectionStatus;
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
    CollectionStatus status;
    List<String> addBagIds;
    List<String> deleteBagIds;

}
