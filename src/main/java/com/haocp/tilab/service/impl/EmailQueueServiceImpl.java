package com.haocp.tilab.service.impl;

import com.haocp.tilab.repository.EmailQueueRepository;
import com.haocp.tilab.service.EmailQueueService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailQueueServiceImpl implements EmailQueueService {

    @Autowired
    EmailQueueRepository emailQueueRepository;

}
