package com.eduvod.eduvod.service.auth.impl;

import com.eduvod.eduvod.dto.auth.PasswordResetRequest;
import com.eduvod.eduvod.dto.auth.PasswordUpdateRequest;
import com.eduvod.eduvod.model.shared.PasswordReset;
import com.eduvod.eduvod.model.shared.User;
import com.eduvod.eduvod.repository.shared.PasswordResetRepository;
import com.eduvod.eduvod.repository.shared.UserRepository;
import com.eduvod.eduvod.service.auth.PasswordResetService;
import com.eduvod.eduvod.service.email.EmailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetRepository tokenRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void requestReset(PasswordResetRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Clear previous tokens
        tokenRepository.deleteByUser(user);

        // Generate token and code
        String token = UUID.randomUUID().toString();
        String code = String.format("%06d", new Random().nextInt(999999));

        PasswordReset reset = PasswordReset.builder()
                .token(token)
                .code(code)
                .user(user)
                .username(user.getActualUsername())
                .email(user.getEmail())
                .expiryDate(LocalDateTime.now().plusHours(1))
                .build();


        tokenRepository.save(reset);

        // Send email with both link + code using Thymeleaf
        emailService.sendPasswordResetEmail(user, token, code);
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
