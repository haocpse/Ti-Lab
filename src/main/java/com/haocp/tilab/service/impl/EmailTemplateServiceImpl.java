package com.haocp.tilab.service.impl;

import com.haocp.tilab.repository.EmailTemplateRepository;
import com.haocp.tilab.service.EmailTemplateService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailTemplateServiceImpl implements EmailTemplateService {

    @Autowired
    EmailTemplateRepository emailTemplateRepository;

    @Override
    public void createMailTemplate() {

    }
}
