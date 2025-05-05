package com.ldlavado.proteccion.service.impl;

import com.ldlavado.proteccion.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Value("${app.email.recipients}")
    private String recipients; // comma-separated list

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(recipients.split("\\s*,\\s*"));
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);
            log.info("Email sent successfully to: {}", recipients);
        } catch (MailException ex) {
            log.error("Failed to send email. Subject: '{}', Error: {}", subject, ex.getMessage(), ex);
            throw new RuntimeException("Failed to send email. Please check SMTP configuration or recipient.");
        } catch (Exception ex) {
            log.error("Unexpected error while sending email: {}", ex.getMessage(), ex);
            throw new RuntimeException("Unexpected error occurred while sending email.");
        }
    }
}
