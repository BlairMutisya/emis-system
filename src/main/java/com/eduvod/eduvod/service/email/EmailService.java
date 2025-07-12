package com.eduvod.eduvod.service.email;

public interface EmailService {
    void send(String to, String subject, String htmlContent);
}
