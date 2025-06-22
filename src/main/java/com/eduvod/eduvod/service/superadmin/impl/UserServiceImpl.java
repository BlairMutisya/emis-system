package com.eduvod.eduvod.service.superadmin.impl;

import com.eduvod.eduvod.dto.response.superadmin.UserResponse;
import com.eduvod.eduvod.enums.UserStatus;
import com.eduvod.eduvod.model.shared.RoleType;
import com.eduvod.eduvod.model.shared.User;
import com.eduvod.eduvod.repository.shared.UserRepository;
import com.eduvod.eduvod.service.superadmin.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    private UserResponse mapToResponse(User user) {
        var isSchoolAdmin = user.getRole() == RoleType.SCHOOL_ADMIN;


        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .status(user.isEnabled() ? UserStatus.ACTIVE : UserStatus.BLOCKED)
                .schoolName(isSchoolAdmin && user.getSchoolAdmin() != null && user.getSchoolAdmin().getSchool() != null
                        ? user.getSchoolAdmin().getSchool().getName()
                        : null)
                .build();
    }
}
