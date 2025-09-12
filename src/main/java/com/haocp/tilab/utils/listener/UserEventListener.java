package com.haocp.tilab.utils.listener;

import com.haocp.tilab.dto.response.Template.TemplateResponse;
import com.haocp.tilab.entity.Customer;
import com.haocp.tilab.entity.EmailTemplate;
import com.haocp.tilab.entity.User;
import com.haocp.tilab.exception.AppException;
import com.haocp.tilab.exception.ErrorCode;
import com.haocp.tilab.repository.CustomerRepository;
import com.haocp.tilab.service.EmailService;
import com.haocp.tilab.service.EmailTemplateService;
import com.haocp.tilab.service.PaymentService;
import com.haocp.tilab.utils.event.OrderCreatedEvent;
import com.haocp.tilab.utils.event.PasswordResetEvent;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEventListener {

    @Value("${app.url}")
    String appUrl;
    final EmailService emailService;
    final CustomerRepository customerRepository;
    final EmailTemplateService emailTemplateService;

    public UserEventListener(EmailService emailService, CustomerRepository customerRepository, EmailTemplateService emailTemplateService) {
        this.emailService = emailService;
        this.customerRepository = customerRepository;
        this.emailTemplateService = emailTemplateService;
    }

    @EventListener
    public void handlePasswordResetEvent(PasswordResetEvent event) throws MessagingException {
        User user = event.getUser();
        Customer customer = customerRepository.findById(user.getId())
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));
        Map<String, String> values = Map.of(
                "customer_name", customer.getFirstName() + " " + customer.getLastName(),
                "reset_link", appUrl + "/reset/" + customer.getId()
        );
        TemplateResponse template = emailTemplateService.getTemplate("PASSWORD_RESET", values);
        emailService.sendMail(user.getEmail(), template.getSubject(), template.getBody());
    }

}
