package com.eduvod.eduvod.service.superadmin.impl;

import com.eduvod.eduvod.dto.request.superadmin.*;
import com.eduvod.eduvod.dto.response.superadmin.SchoolAdminResponse;
import com.eduvod.eduvod.enums.SchoolAdminStatus;
import com.eduvod.eduvod.model.superadmin.SchoolAdmin;
import com.eduvod.eduvod.repository.superadmin.*;
import com.eduvod.eduvod.service.superadmin.SchoolAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SchoolAdminServiceImpl implements SchoolAdminService {

    private final SchoolAdminRepository repository;
    private final SchoolRepository schoolRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SchoolAdminResponse createSchoolAdmin(SchoolAdminRequest request) {
        var admin = SchoolAdmin.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .status(SchoolAdminStatus.ACTIVE)
                .build();

        if (request.getSchoolId() != null) {
            admin.setSchool(schoolRepository.findById(request.getSchoolId())
                    .orElseThrow(() -> new RuntimeException("School not found")));
        }

        repository.save(admin);
        return mapToResponse(admin);
    }


    @Override
    public List<SchoolAdminResponse> getAllSchoolAdmins() {
        return repository.findAll().stream()
                .filter(admin -> !admin.isDeleted())
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }


    @Override
    public void updateStatus(Long adminId, SchoolAdminStatus status) {
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
