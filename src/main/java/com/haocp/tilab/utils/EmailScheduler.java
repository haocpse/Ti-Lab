package com.haocp.tilab.utils;

import com.haocp.tilab.entity.EmailQueue;
import com.haocp.tilab.enums.EmailStatus;
import com.haocp.tilab.repository.EmailQueueRepository;
import com.haocp.tilab.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailScheduler {

    @Autowired
    EmailQueueRepository emailQueueRepository;
    @Autowired
    EmailService emailService;

    @Scheduled(fixedRate = 60000)
    public void processEmailQueue() {
//        List<EmailQueue> pendingEmails = emailQueueRepository;
//
//        for (EmailQueue email : pendingEmails) {
//            try {
//                emailService.sendSimpleMail(email.getRecipient(), email.getSubject(), email.getBody());
//                email.setStatus(EmailStatus.SENT);
//                email.setSentAt(Instant.now());
//            } catch (MessagingException e) {
//                email.setStatus(EmailStatus.FAILED);
//                email.setErrorMessage(e.getMessage());
//                email.setRetryCount(email.getRetryCount() + 1);
//            }
//            emailQueueRepository.save(email);
//        }
    }

}
