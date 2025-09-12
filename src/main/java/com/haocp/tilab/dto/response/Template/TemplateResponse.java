package com.haocp.tilab.dto.response.Template;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TemplateResponse {

    String subject;
    String body;

}
