package com.eduvod.eduvod.service.superadmin.impl;

import com.eduvod.eduvod.constants.ErrorMessages;
import com.eduvod.eduvod.dto.request.superadmin.*;
import com.eduvod.eduvod.dto.response.common.BaseApiResponse;
import com.eduvod.eduvod.dto.response.superadmin.AssignSchoolResponse;
import com.eduvod.eduvod.dto.response.superadmin.SchoolAdminResponse;
import com.eduvod.eduvod.enums.UserStatus;
import com.eduvod.eduvod.exception.ResourceNotFoundException;
import com.eduvod.eduvod.model.shared.RoleType;
import com.eduvod.eduvod.model.shared.User;
import com.eduvod.eduvod.model.superadmin.School;
import com.eduvod.eduvod.model.superadmin.SchoolAdmin;
import com.eduvod.eduvod.repository.shared.UserRepository;
import com.eduvod.eduvod.repository.superadmin.*;
import com.eduvod.eduvod.service.superadmin.SchoolAdminService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.eduvod.eduvod.service.email.EmailService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SchoolAdminServiceImpl implements SchoolAdminService {

    private final SchoolAdminRepository repository;
    private final SchoolRepository schoolRepository;
    private final PasswordEncoder passwordEncoder;
    private final SchoolAdminRepository schoolAdminRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;


    public SchoolAdminResponse createSchoolAdmin(SchoolAdminRequest request) {
        Optional<User> existingUserOpt = userRepository.findByEmail(request.getEmail());
        User user;
        boolean isNewUser = false;

        if (existingUserOpt.isPresent()) {
            user = existingUserOpt.get();
        } else {
            isNewUser = true;
            String generatedPassword = generateRandomPassword();

            user = User.builder()
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(generatedPassword))
                    .status(UserStatus.ACTIVE)
                    .role(RoleType.SCHOOL_ADMIN)
                    .mustChangePassword(true)
                    .build();

            user = userRepository.save(user);

            // Send invitation only for new users
            emailService.sendInvitationEmail(user, generatedPassword);
        }

        Optional<SchoolAdmin> existingAdminOpt = repository.findByUserId(user.getId());
        SchoolAdmin admin;

        if (existingAdminOpt.isPresent()) {
            admin = existingAdminOpt.get();
        } else {
            admin = SchoolAdmin.builder()
                    .user(user)
                    .status(UserStatus.ACTIVE)
                    .build();
        }

        if (request.getSchoolId() != null) {
            School school = schoolRepository.findById(request.getSchoolId())
                    .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.SCHOOL_NOT_FOUND));
            admin.setSchool(school);
        }

        // Ensure admin is saved
        admin = repository.save(admin);

        return mapToResponse(admin);
    }



    private String generateRandomPassword() {
        return UUID.randomUUID().toString()
                .replace("-", "")
                .substring(0, 10);
    }


    @Override
    public List<SchoolAdminResponse> getAllSchoolAdmins() {
        return repository.findAll().stream()
                .filter(admin -> !admin.isDeleted())
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }


    @Override
    public void updateStatus(Long adminId, UserStatus status) {
        var admin = getById(adminId);
        admin.setStatus(status);
        repository.save(admin);
    }


    @Override
    public void softDelete(Long id) {
        var admin = getById(id);
        admin.setDeletedAt(LocalDateTime.now());
        repository.save(admin);
    }

    @Override
    public void updatePassword(Long id, UpdateSchoolAdminPasswordRequest request) {
        var admin = getById(request.getSchoolAdminId());
        admin.getUser().setPassword(passwordEncoder.encode(request.getNewPassword()));
        repository.save(admin);
    }


    @Override
    public BaseApiResponse<AssignSchoolResponse> assignSchool(AssignSchoolRequest request) {
        // Fetch SchoolAdmin with associated User
        SchoolAdmin admin = repository.findById(request.getSchoolAdminId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.SCHOOL_ADMIN_NOT_FOUND));

        // Fetch School
        School school = schoolRepository.findById(request.getSchoolId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.SCHOOL_NOT_FOUND));

        // Avoid re-assigning the same school
        if (admin.getSchool() != null && admin.getSchool().getId().equals(school.getId())) {
            throw new IllegalStateException(ErrorMessages.SCHOOL_ALREADY_ASSIGNED);
        }

        // Assign the school
        admin.setSchool(school);
        repository.save(admin);

        // Notify the admin if email is available and not blank
        User user = admin.getUser();
        if (user != null && user.getEmail() != null && !user.getEmail().isBlank()) {
            emailService.sendSchoolAssignmentEmail(user, school);
        }

        // Build response
        AssignSchoolResponse response = AssignSchoolResponse.builder()
                .schoolAdminId(admin.getId())
                .username(user != null ? user.getUsername() : null)
                .email(user != null ? user.getEmail() : null)
                .schoolId(school.getId())
                .schoolName(school.getName())
                .message("School assigned successfully")
                .build();

        return new BaseApiResponse<>(200, "School assignment successful", response);
    }

    @Override
    public BaseApiResponse<String> unassignSchool(Long schoolAdminId) {
        SchoolAdmin admin = schoolAdminRepository.findById(schoolAdminId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.SCHOOL_ADMIN_NOT_FOUND));

        School school = admin.getSchool(); // Capture school before unassignment

        if (school != null) {
            String location = String.format(
                    "%s, %s, %s",
                    school.getSubCounty() != null ? school.getSubCounty().getName() : "N/A",
                    school.getCounty() != null ? school.getCounty().getName() : "N/A",
                    school.getRegion() != null ? school.getRegion().getName() : "N/A"
            );

            // Send unassignment email
            emailService.sendSchoolUnassignmentEmail(admin.getUser(), school.getName(), school.getMoeRegNo(), location);
        }

        admin.setSchool(null);
        schoolAdminRepository.save(admin);

        return new BaseApiResponse<>(200, "School unassigned successfully", null);
    }
    private SchoolAdmin getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.SCHOOL_ADMIN_NOT_FOUND));
    }

    private SchoolAdminResponse mapToResponse(SchoolAdmin admin) {
        return SchoolAdminResponse.builder()
                .id(admin.getId())
                .username(admin.getUser().getUsername())
                .email(admin.getUser().getEmail())
                .schoolName(admin.getSchool() != null ? admin.getSchool().getName() : null)
                .status(admin.getStatus())
                .build();
    }
}
