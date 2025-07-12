package com.eduvod.eduvod.service.auth.impl;

import com.eduvod.eduvod.dto.auth.PasswordResetRequest;
import com.eduvod.eduvod.dto.auth.PasswordUpdateRequest;
import com.eduvod.eduvod.model.shared.PasswordReset;
import com.eduvod.eduvod.model.shared.User;
import com.eduvod.eduvod.repository.shared.PasswordResetRepository;
import com.eduvod.eduvod.repository.shared.UserRepository;
import com.eduvod.eduvod.service.auth.PasswordResetService;
import com.eduvod.eduvod.service.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetRepository tokenRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void requestReset(PasswordResetRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Delete old tokens
        tokenRepository.deleteByUser(user);

        String token = UUID.randomUUID().toString();

        PasswordReset resetToken = PasswordReset.builder()
                .token(token)
                .user(user)
                .expiryDate(LocalDateTime.now().plusHours(1))
                .build();

        tokenRepository.save(resetToken);

        String resetLink = "https://eduvod.com/reset-password?token=" + token;

        String message = "<p>Hello,</p><p>Click below to reset your password:</p>" +
                "<a href=\"" + resetLink + "\">Reset Password</a>" +
                "<p>This link expires in 1 hour.</p>";

        emailService.send(user.getEmail(), "Reset your EduVOD password", message);
    }

    @Override
    public void updatePassword(PasswordUpdateRequest request) {
        PasswordReset token = tokenRepository.findByToken(request.getToken())
                .orElseThrow(() -> new RuntimeException("Invalid or expired token"));

        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token has expired");
        }

        User user = token.getUser();
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);
        tokenRepository.delete(token); // invalidate token
    }
}
