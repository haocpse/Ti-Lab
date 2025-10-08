package com.haocp.tilab.utils.listener;

import com.haocp.tilab.dto.response.Template.TemplateResponse;
import com.haocp.tilab.entity.Customer;
import com.haocp.tilab.entity.Order;
import com.haocp.tilab.entity.Payment;
import com.haocp.tilab.entity.User;
import com.haocp.tilab.exception.AppException;
import com.haocp.tilab.exception.ErrorCode;
import com.haocp.tilab.repository.CustomerRepository;
import com.haocp.tilab.service.EmailService;
import com.haocp.tilab.service.EmailTemplateService;
import com.haocp.tilab.utils.event.ConfirmPaidEvent;
import com.haocp.tilab.utils.event.ConfirmRegisterEvent;
import com.haocp.tilab.utils.event.PasswordResetEvent;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailEventListener {

    @Value("${app.url}")
    String appUrl;
    final EmailService emailService;
    final CustomerRepository customerRepository;
    final EmailTemplateService emailTemplateService;

    public EmailEventListener(EmailService emailService, CustomerRepository customerRepository, EmailTemplateService emailTemplateService) {
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

    @EventListener
    public void handleConfirmPaidEvent(ConfirmPaidEvent event) throws MessagingException {
        String email = event.getEmail();
        Order order = event.getOrder();
        Payment payment = event.getPayment();
        Customer customer = event.getCustomer();
        Instant paymentDate = payment.getPayAt();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = LocalDateTime.from(paymentDate).format(formatter);
        Map<String, String> values = Map.of(
                "customer_name", customer.getFirstName() + " " + customer.getLastName(),
                "order_id", order.getId(),
                "payment_date", formattedDate,
                "payment_method", payment.getMethod().toString(),
                "payment_amount", Double.toString(payment.getTotal()),
                "order_url", ""
        );
        TemplateResponse template = emailTemplateService.getTemplate("CONFIRM_PAID", values);
        emailService.sendMail(email, template.getSubject(), template.getBody());
    }

    @EventListener
    public void handleConfirmRegister(ConfirmRegisterEvent event) throws MessagingException {
        Customer customer = event.getCustomer();
        String verifyUrl = appUrl + "api/verify?token=" + event.getToken() + "&userId=" + customer.getId();
        Map<String, String> values = Map.of(
                "customer_name", customer.getFirstName() + " " + customer.getLastName(),
                "verification_url", verifyUrl
        );
        TemplateResponse template = emailTemplateService.getTemplate("VERIFY_ACCOUNT", values);
        emailService.sendMail(customer.getUser().getEmail(), template.getSubject(), template.getBody());
    }

}
