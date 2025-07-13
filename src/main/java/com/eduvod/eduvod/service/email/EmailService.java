package com.eduvod.eduvod.service.email;

import com.eduvod.eduvod.model.shared.User;

public interface EmailService {
    void send(String to, String subject, String htmlContent);
    void sendPasswordResetEmail(User user, String token, String code);

}
