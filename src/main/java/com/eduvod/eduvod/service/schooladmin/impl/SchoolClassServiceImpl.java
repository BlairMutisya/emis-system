package com.eduvod.eduvod.service.schooladmin.impl;

import com.eduvod.eduvod.dto.request.schooladmin.CreateClassRequest;
import com.eduvod.eduvod.dto.response.BaseApiResponse;
import com.eduvod.eduvod.dto.response.schooladmin.ClassResponse;
import com.eduvod.eduvod.model.schooladmin.SchoolClass;
import com.eduvod.eduvod.repository.schooladmin.SchoolClassRepository;
import com.eduvod.eduvod.repository.superadmin.GradeRepository;
import com.eduvod.eduvod.security.util.AuthUtil;
import com.eduvod.eduvod.service.schooladmin.SchoolClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SchoolClassServiceImpl implements SchoolClassService {

    private final SchoolClassRepository schoolClassRepository;
    private final GradeRepository gradeRepository;
    private final AuthUtil authUtil;


    @Override
    public BaseApiResponse<ClassResponse> createClass(CreateClassRequest request) {
        var grade = gradeRepository.findById(request.getGradeId())
                .orElseThrow(() -> new RuntimeException("Grade not found"));

        var schoolAdmin = authUtil.getCurrentSchoolAdmin();
        var school = schoolAdmin.getSchool();

        var className = "Class of " + request.getAcademicYear() + " " + grade.getName();

        var newClass = SchoolClass.builder()
                .academicYear(request.getAcademicYear())
                .grade(grade)
                .school(school)
                .name(className)
                .build();

        schoolClassRepository.save(newClass);

        return BaseApiResponse.success(mapToResponse(newClass));
    }

    @Override
    public BaseApiResponse<List<ClassResponse>> getAllClassesForSchool() {
        var schoolAdmin = authUtil.getCurrentSchoolAdmin();
        var school = schoolAdmin.getSchool();

        var classes = schoolClassRepository.findBySchool(school)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return BaseApiResponse.success(classes);
    }

    private ClassResponse mapToResponse(SchoolClass schoolClass) {
        return ClassResponse.builder()
                .id(schoolClass.getId())
                .name(schoolClass.getName())
                .academicYear(schoolClass.getAcademicYear())
                .gradeName(schoolClass.getGrade().getName())
                .build();
    }
}
