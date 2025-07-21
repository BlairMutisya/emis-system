package com.eduvod.eduvod.service.schooladmin.impl;

import com.eduvod.eduvod.dto.response.common.PagedResponse;
import com.eduvod.eduvod.dto.response.schooladmin.SchoolResponse;
import com.eduvod.eduvod.model.superadmin.School;
import com.eduvod.eduvod.repository.superadmin.SchoolRepository;
import com.eduvod.eduvod.repository.superadmin.SchoolAdminRepository;
import com.eduvod.eduvod.security.util.AuthUtil;
import com.eduvod.eduvod.service.schooladmin.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolAdminSchoolServiceImpl implements SchoolService {

    private final SchoolRepository schoolRepository;
    private final SchoolAdminRepository schoolAdminRepository;
    private final AuthUtil authUtil;

    private static final String LOGO_UPLOAD_DIR = "uploads/logos/";

    @Override
    public SchoolResponse getSchoolForLoggedInAdmin() {
        var schoolAdmin = authUtil.getCurrentSchoolAdmin();
        School school = schoolAdmin.getSchool();

        if (school == null) throw new RuntimeException("School not assigned");

        return mapToResponse(school);
    }

    @Override
    public SchoolResponse updateSchoolLogo(MultipartFile file) {
        var schoolAdmin = authUtil.getCurrentSchoolAdmin();
        School school = schoolAdmin.getSchool();

        if (school == null) throw new RuntimeException("School not assigned");

        try {
            // Save file
            String fileName = "school_" + school.getId() + "_" + file.getOriginalFilename();
            Path uploadPath = Paths.get(LOGO_UPLOAD_DIR);

            if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath);

            // Set logo URL (you can serve this via a static resource handler)
            String logoUrl = "/logos/" + fileName;
            school.setLogoUrl(logoUrl);
            schoolRepository.save(school);

            return mapToResponse(school);

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload logo", e);
        }
    }

    private SchoolResponse mapToResponse(School school) {
        return SchoolResponse.builder()
                .id(school.getId())
                .name(school.getName())
                .email(school.getEmail())
                .phone(school.getPhone())
                .address(school.getAddress())
                .logoUrl(school.getLogoUrl())
                .category(school.getCategory().getName())
                .curriculumType(school.getCurriculum().getName())
                .schoolType(school.getType().getName())
                .build();
    }


}
