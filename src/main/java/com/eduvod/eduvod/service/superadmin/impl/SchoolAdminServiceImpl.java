package com.eduvod.eduvod.service.superadmin.impl;

import com.eduvod.eduvod.dto.request.superadmin.*;
import com.eduvod.eduvod.dto.response.BaseApiResponse;
import com.eduvod.eduvod.dto.response.superadmin.SchoolAdminResponse;
import com.eduvod.eduvod.enums.UserStatus;
import com.eduvod.eduvod.model.shared.RoleType;
import com.eduvod.eduvod.model.shared.User;
import com.eduvod.eduvod.model.superadmin.School;
import com.eduvod.eduvod.model.superadmin.SchoolAdmin;
import com.eduvod.eduvod.repository.shared.UserRepository;
import com.eduvod.eduvod.repository.superadmin.*;
import com.eduvod.eduvod.service.superadmin.SchoolAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.eduvod.eduvod.service.email.EmailService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SchoolAdminServiceImpl implements SchoolAdminService {

    private final SchoolAdminRepository repository;
    private final SchoolRepository schoolRepository;
    private final PasswordEncoder passwordEncoder;
    private final SchoolAdminRepository schoolAdminRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;


    @Override
    public SchoolAdminResponse createSchoolAdmin(SchoolAdminRequest request) {
        // 1. Create and save the User
        String generatedPassword = generateRandomPassword();

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(generatedPassword))
                .status(UserStatus.ACTIVE)
                .role(RoleType.SCHOOL_ADMIN)
                .mustChangePassword(true)
                .build();

        user = userRepository.save(user);
        emailService.sendInvitationEmail(user, generatedPassword);


        // 2. Create SchoolAdmin and associate the User
        var admin = SchoolAdmin.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(user.getPassword())
                .user(user)
                .status(UserStatus.ACTIVE)
                .build();

        // 3. Attach school if ID is given
        if (request.getSchoolId() != null) {
            admin.setSchool(schoolRepository.findById(request.getSchoolId())
                    .orElseThrow(() -> new RuntimeException("School not found")));
        }

        repository.save(admin);

        return mapToResponse(admin);
    }


    private String generateRandomPassword() {
        return UUID.randomUUID().toString()
                .replace("-", "")
                .substring(0, 10); // 10-character password
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
        var admin = getById(request.getAdminId());
        admin.setPassword(passwordEncoder.encode(request.getNewPassword()));
        repository.save(admin);
    }
    @Override
    public void assignSchool(AssignSchoolRequest request) {
        var admin = getById(request.getSchoolAdminId());
        var school = schoolRepository.findById(request.getSchoolId())
                .orElseThrow(() -> new RuntimeException("School not found"));

        admin.setSchool(school);
        repository.save(admin);

        // Send assignment notification email
        emailService.sendSchoolAssignmentEmail(admin.getUser(), school);
    }


    @Override
    public BaseApiResponse<String> unassignSchool(Long schoolAdminId) {
        SchoolAdmin admin = schoolAdminRepository.findById(schoolAdminId)
                .orElseThrow(() -> new RuntimeException("School admin not found"));

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
        return repository.findById(id).orElseThrow(() -> new RuntimeException("School admin not found"));
    }

    private SchoolAdminResponse mapToResponse(SchoolAdmin admin) {
        return SchoolAdminResponse.builder()
                .id(admin.getId())
                .username(admin.getUsername())
                .email(admin.getEmail())
                .schoolName(admin.getSchool() != null ? admin.getSchool().getName() : null)
                .status(admin.getStatus())
                .build();
    }
}
