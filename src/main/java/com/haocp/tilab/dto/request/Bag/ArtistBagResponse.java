package com.haocp.tilab.dto.request.Bag;

import com.haocp.tilab.dto.response.Bag.BagResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ArtistBagResponse {

    String name;
    List<BagResponse> bags;

}
