package com.eduvod.eduvod.service.email.impl;

import com.eduvod.eduvod.model.shared.User;
import com.eduvod.eduvod.model.superadmin.School;
import com.eduvod.eduvod.service.email.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${app.frontend.login-url}")
    private String frontendLoginUrl;

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

    @Override
    public void sendPasswordResetEmail(User user, String token, String code) {
        Context context = new Context();
        context.setVariable("username", user.getActualUsername());
        String resetLink = frontendResetUrl + "?token=" + token;
        context.setVariable("resetLink", resetLink);
        context.setVariable("resetCode", code);

        String html = templateEngine.process("password-reset-email.html", context);
        send(user.getEmail(), "Reset Your EduVOD Password", html);
    }

    @Override
    public void sendInvitationEmail(User user, String temporaryPassword) {
        Context context = new Context();
        context.setVariable("username", user.getActualUsername());
        context.setVariable("temporaryPassword", temporaryPassword);
        context.setVariable("role", user.getRole().name());
        context.setVariable("loginLink", frontendLoginUrl);

        String html = templateEngine.process("invitation-email.html", context);
        send(user.getEmail(), "Welcome to EduVOD!", html);
    }

    @Override
    public void sendSchoolAssignmentEmail(User user, School school) {
        Context context = new Context();
        context.setVariable("username", user.getActualUsername());
        context.setVariable("schoolName", school.getName());
        context.setVariable("moeRegNo", school.getMoeRegNo());

        String location = String.format(
                "%s, %s, %s",
                school.getSubCounty() != null ? school.getSubCounty().getName() : "N/A",
                school.getCounty() != null ? school.getCounty().getName() : "N/A",
                school.getRegion() != null ? school.getRegion().getName() : "N/A"
        );
        context.setVariable("location", location);
        context.setVariable("loginLink", frontendLoginUrl);

        String html = templateEngine.process("school-assignment-email.html", context);
        send(user.getEmail(), "You Have Been Assigned to a School", html);
    }
    @Override
    public void sendSchoolUnassignmentEmail(User user, String schoolName, String moeRegNo, String location) {
        Context context = new Context();
        context.setVariable("username", user.getActualUsername());
        context.setVariable("schoolName", schoolName);
        context.setVariable("moeRegNo", moeRegNo);
        context.setVariable("location", location);
        context.setVariable("loginLink", frontendLoginUrl);

        String html = templateEngine.process("school-unassignment-email.html", context);
        send(user.getEmail(), "You Have Been Unassigned from a School", html);
    }

}
