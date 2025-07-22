package com.eduvod.eduvod.security.service;

import com.eduvod.eduvod.constants.ErrorMessages;
import com.eduvod.eduvod.dto.auth.AuthenticationRequest;
import com.eduvod.eduvod.dto.auth.AuthenticationResponse;
import com.eduvod.eduvod.dto.auth.RegisterRequest;
import com.eduvod.eduvod.enums.UserStatus;
import com.eduvod.eduvod.exception.UserAccountBlockedException;
import com.eduvod.eduvod.exception.UserAccountDeletedException;
import com.eduvod.eduvod.exception.UserNotFoundException;
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
                .active(true)
                .mustChangePassword(false)
                .build();

        userRepository.save(user);
        return buildAuthResponse(user);
    }


    // SchoolAdmin Registration
    public AuthenticationResponse registerSchoolAdmin(RegisterRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(RoleType.SCHOOL_ADMIN)
                .status(UserStatus.ACTIVE)
                .active(true)
                .mustChangePassword(true)
                .build();
        userRepository.save(user);

        return buildAuthResponse(user);

    }


    // Common Login
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND));

        //Block login if status is BLOCKED or DELETED
        if (user.getStatus() == UserStatus.BLOCKED) {
            throw new UserAccountBlockedException(ErrorMessages.USER_BLOCKED);
        }

        if (user.getStatus() == UserStatus.DELETED) {
            throw new UserAccountDeletedException(ErrorMessages.USER_DELETED);
        }


        // Proceed if ACTIVE
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        return buildAuthResponse(user);
    }
    private AuthenticationResponse buildAuthResponse(User user) {
        var jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwt)
                .userId(user.getId())
                .username(user.getActualUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .mustChangePassword(user.isMustChangePassword())
                .build();
    }


}

