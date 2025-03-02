package com.etour.etour_api.service.implementation;

import com.etour.etour_api.exception.ApiException;
import com.etour.etour_api.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.etour.etour_api.utils.EmailUtils.getNewAccountEmailMessage;
import static com.etour.etour_api.utils.EmailUtils.getResetPasswordEmailMessage;

/**
 * @version 1.0
 * @project etour-api
 * @since 27-01-2025
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private static final String NEW_USER_ACCOUNT_VERIFICATION = "New User Account Verification";
    private static final String PASSWORD_RESET_REQUEST = "Reset Password Request";

    private final JavaMailSender mailSender;

    @Value(value = "${spring.mail.verify.host}")
    private String host;
    @Value(value = "${spring.mail.username}")
    private String fromEmail;

    @Async
    @Override
    public void sendNewAccountEmail(String name, String email, String key) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            message.setFrom(fromEmail);
            message.setTo(email);
            message.setText(getNewAccountEmailMessage(name, host, key));
            mailSender.send(message);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("Unable to send email");
        }
    }

    @Async
    @Override
    public void sendPasswordResetEmail(String name, String email, String key) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(PASSWORD_RESET_REQUEST);
            message.setFrom(fromEmail);
            message.setTo(email);
            message.setText(getResetPasswordEmailMessage(name, host, key));
            mailSender.send(message);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("Unable to send email");
        }
    }
}
