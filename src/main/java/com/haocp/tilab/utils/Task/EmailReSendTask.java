package com.haocp.tilab.utils.Task;

import com.haocp.tilab.repository.EmailQueueRepository;
import com.haocp.tilab.service.EmailService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailReSendTask {

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
