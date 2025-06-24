package com.eduvod.eduvod.security.util;

import com.eduvod.eduvod.model.superadmin.SchoolAdmin;
import com.eduvod.eduvod.model.shared.User;
import com.eduvod.eduvod.repository.superadmin.SchoolAdminRepository;
import com.eduvod.eduvod.repository.shared.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtil {

    private final UserRepository userRepository;
    private final SchoolAdminRepository schoolAdminRepository;

    /**
     * Retrieves the currently authenticated user from the Spring Security context.
     */
    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Authenticated user not found"));
    }

    /**
     * Retrieves the SchoolAdmin associated with the currently authenticated user.
     */
    public SchoolAdmin getCurrentSchoolAdmin() {
        User user = getCurrentUser();
        return schoolAdminRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("School Admin not found for user: " + user.getEmail()));
    }
}
