package com.haocp.tilab.dto.request.Collection;

import com.haocp.tilab.dto.request.Bag.CreateBagRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateCollectionRequest {

    String name;
    List<CreateBagRequest> createBagRequests;

}
