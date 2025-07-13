package com.eduvod.eduvod.service.email.impl;
import org.springframework.beans.factory.annotation.Value;

import com.eduvod.eduvod.model.shared.User;
import com.eduvod.eduvod.service.email.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    @Value("${app.frontend.reset-url}")
    private String frontendResetUrl;


    @Override
    public void send(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            helper.setFrom("no-reply@eduvod.com");

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    //Method to send reset password emails with Thymeleaf template
    @Override
    public void sendPasswordResetEmail(User user, String token, String code) {
        String resetLink = frontendResetUrl + "?token=" + token;
        Context context = new Context();
        context.setVariable("username", user.getActualUsername());
        context.setVariable("resetLink", resetLink);
        context.setVariable("resetCode", code);

        String html = templateEngine.process("password-reset-email.html", context);
        send(user.getEmail(), "Reset Your EduVOD Password", html);
    }

}
