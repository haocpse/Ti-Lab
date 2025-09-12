package com.haocp.tilab.service.impl;

import com.haocp.tilab.dto.request.Template.CreateTemplateRequest;
import com.haocp.tilab.dto.response.Template.TemplateResponse;
import com.haocp.tilab.entity.EmailTemplate;
import com.haocp.tilab.exception.AppException;
import com.haocp.tilab.exception.ErrorCode;
import com.haocp.tilab.repository.EmailTemplateRepository;
import com.haocp.tilab.service.EmailTemplateService;
import com.haocp.tilab.utils.ReplaceVariables;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailTemplateServiceImpl implements EmailTemplateService {

    @Autowired
    EmailTemplateRepository emailTemplateRepository;
    @Autowired
    ReplaceVariables replaceVariables;

    @Override
    public void createMailTemplate(CreateTemplateRequest request, MultipartFile bodyFile) {
        try {
            String body = new String(bodyFile.getBytes(), StandardCharsets.UTF_8);
            EmailTemplate template = EmailTemplate.builder()
                    .code(request.getCode())
                    .subject(request.getSubject())
                    .body(body)
                    .variables(request.getVariables())
                    .build();
            emailTemplateRepository.save(template);
        } catch (IOException e){
            throw new RuntimeException("Failed to send email", e);
        }
    }

    @Override
    public TemplateResponse getTemplate(String code, Map<String, String> values) {
        EmailTemplate template = emailTemplateRepository.findByCode(code)
                .orElseThrow(() -> new AppException(ErrorCode.TEMPLATE_NOT_EXIST));
        String body = replaceVariables.replace(template.getBody(), values);
        return TemplateResponse.builder()
                .body(body)
                .subject(template.getSubject())
                .build();
    }
}
