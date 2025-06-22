package com.eduvod.eduvod.security.service;

import com.eduvod.eduvod.dto.auth.AuthenticationRequest;
import com.eduvod.eduvod.dto.auth.AuthenticationResponse;
import com.eduvod.eduvod.dto.auth.RegisterRequest;
import com.eduvod.eduvod.enums.UserStatus;
import com.eduvod.eduvod.model.shared.RoleType;
import com.eduvod.eduvod.model.shared.User;
import com.eduvod.eduvod.repository.shared.UserRepository;
import com.eduvod.eduvod.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // SuperAdmin Registration
    public AuthenticationResponse registerSuperAdmin(RegisterRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(RoleType.SUPER_ADMIN)
                .status(UserStatus.ACTIVE)
                .build();
        userRepository.save(user);

        var jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwt).build();
    }

    // SchoolAdmin Registration
    public AuthenticationResponse registerSchoolAdmin(RegisterRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(RoleType.SCHOOL_ADMIN)
                .status(UserStatus.ACTIVE)
                .build();
        userRepository.save(user);

        var jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwt).build();
    }

    // Common Login
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        //Block login if status is BLOCKED or DELETED
        if (user.getStatus() == UserStatus.BLOCKED) {
            throw new RuntimeException("Your account is blocked. Contact support.");
        }
        if (user.getStatus() == UserStatus.DELETED) {
            throw new RuntimeException("Your account has been deleted.");
        }

        // Proceed if ACTIVE
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        var jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwt).build();
    }

}

