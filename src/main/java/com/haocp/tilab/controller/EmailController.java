package com.haocp.tilab.controller;

import com.haocp.tilab.dto.ApiResponse;
import com.haocp.tilab.dto.request.Template.CreateTemplateRequest;
import com.haocp.tilab.entity.EmailTemplate;
import com.haocp.tilab.service.EmailService;
import com.haocp.tilab.service.EmailTemplateService;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/emails/")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailController {

    @Autowired
    EmailTemplateService emailTemplateService;
    @Autowired
    EmailService emailService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<Void> createTemplate(@RequestPart(value = "template")CreateTemplateRequest request,
                                                     @RequestPart MultipartFile body) {
        emailTemplateService.createMailTemplate(request, body);
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("send")
    public ApiResponse<Void> sendMailTest(@RequestPart(value = "to") String to,
                                          @RequestPart(value = "subject") String subject,
                                          @RequestPart(value = "body") String body) throws MessagingException {
        emailService.sendMail(to, subject, body);
        return ApiResponse.<Void>builder().build();
    }

}
