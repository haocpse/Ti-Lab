package com.haocp.tilab.controller;

import com.haocp.tilab.dto.ApiResponse;
import com.haocp.tilab.entity.EmailTemplate;
import com.haocp.tilab.service.EmailTemplateService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/emails/")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailController {

    @Autowired
    EmailTemplateService emailTemplateService;

    public ApiResponse<EmailTemplate> createTemplate(){
        return ApiResponse.<EmailTemplate>builder()
                .build();
    }

}
