package com.haocp.tilab.dto.response.Collection;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CollectionResponse {

    long id;
    String name;
    String urlThumbnail;

}
