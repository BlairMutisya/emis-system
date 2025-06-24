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

    public SchoolAdmin getCurrentSchoolAdmin() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return schoolAdminRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("School Admin not found for user"));
    }
}
