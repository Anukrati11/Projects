package com.example.AppInstallationSystem.service;

import com.example.AppInstallationSystem.entity.App;
import com.example.AppInstallationSystem.exception.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

// This service is responsible for sending email notifications when an app installation fails.
@Service
public class EmailNotificationService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendFailureNotification(App app, String errorMessage) throws MessagingException, jakarta.mail.MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo("anuverksp@example.com");
        helper.setSubject("App Installation Failed");
        helper.setText(String.format("App Name: %s\nError: %s\nRetries: %d\nLast Updated: %s",
                app.getName(), errorMessage, app.getRetryCount(), app.getLastUpdated()));
        mailSender.send(message);
    }
}
