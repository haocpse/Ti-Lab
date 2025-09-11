package com.haocp.tilab.service;

import jakarta.mail.MessagingException;

public interface EmailService {

    void sendSimpleMail(String to, String subject, String text) throws MessagingException;

}
