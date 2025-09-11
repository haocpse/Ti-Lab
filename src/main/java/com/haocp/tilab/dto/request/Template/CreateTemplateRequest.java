package com.haocp.tilab.dto.request.Template;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateTemplateRequest {

    String code;
    String subject;
    String variables;

}
