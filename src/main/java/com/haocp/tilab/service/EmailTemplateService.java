package com.haocp.tilab.service;

import com.haocp.tilab.dto.request.Template.CreateTemplateRequest;
import com.haocp.tilab.dto.response.Template.TemplateResponse;
import com.haocp.tilab.entity.EmailTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface EmailTemplateService {

    void createMailTemplate(CreateTemplateRequest request, MultipartFile body);
    TemplateResponse getTemplate(String code, Map<String, Object> values);

}
