package com.eduvod.eduvod.service.superadmin.impl;

import com.eduvod.eduvod.dto.response.superadmin.UserResponse;
import com.eduvod.eduvod.enums.UserStatus;
import com.eduvod.eduvod.model.shared.RoleType;
import com.eduvod.eduvod.model.shared.User;
import com.eduvod.eduvod.repository.shared.UserRepository;
import com.eduvod.eduvod.service.superadmin.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void changeStatus(Long userId, UserStatus status) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setStatus(status);
        userRepository.save(user);
    }

    @Override
    public void softDeleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    private UserResponse mapToResponse(User user) {
        boolean isSchoolAdmin = user.getRole() == RoleType.SCHOOL_ADMIN;

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getActualUsername()) // 👈 actual username from DB
                .email(user.getEmail())             // 👈 email used for login
                .role(user.getRole())
                .status(user.getStatus())
                .schoolName(
                        isSchoolAdmin && user.getSchoolAdmin() != null && user.getSchoolAdmin().getSchool() != null
                                ? user.getSchoolAdmin().getSchool().getName()
                                : null
                )
                .build();
    }

}
