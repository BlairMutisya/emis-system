package com.eduvod.eduvod.service.schooladmin.impl;

import com.eduvod.eduvod.dto.request.schooladmin.CreateClassRequest;
import com.eduvod.eduvod.dto.response.BaseApiResponse;
import com.eduvod.eduvod.dto.response.schooladmin.ClassResponse;
import com.eduvod.eduvod.model.schooladmin.Class;
import com.eduvod.eduvod.repository.schooladmin.ClassRepository;
import com.eduvod.eduvod.repository.superadmin.GradeRepository;
import com.eduvod.eduvod.security.util.AuthUtil;
import com.eduvod.eduvod.service.schooladmin.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {

    private final ClassRepository classRepository;
    private final GradeRepository gradeRepository;
    private final AuthUtil authUtil;


    @Override
    public BaseApiResponse<ClassResponse> createClass(CreateClassRequest request) {
        var grade = gradeRepository.findById(request.getGradeId())
                .orElseThrow(() -> new RuntimeException("Grade not found"));

        var schoolAdmin = authUtil.getCurrentSchoolAdmin();
        var school = schoolAdmin.getSchool();

        var className = "Class of " + request.getAcademicYear() + " " + grade.getName();

        var newClass = Class.builder()
                .academicYear(request.getAcademicYear())
                .grade(grade)
                .school(school)
                .name(className)
                .build();

        classRepository.save(newClass);

        return BaseApiResponse.success(mapToResponse(newClass));
    }

    @Override
    public BaseApiResponse<List<ClassResponse>> getAllClassesForSchool() {
        var schoolAdmin = authUtil.getCurrentSchoolAdmin();
        var school = schoolAdmin.getSchool();

        var classes = classRepository.findBySchool(school)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return BaseApiResponse.success(classes);
    }

    private ClassResponse mapToResponse(Class clazz) {
        return ClassResponse.builder()
                .id(clazz.getId())
                .name(clazz.getName())
                .academicYear(clazz.getAcademicYear())
                .gradeName(clazz.getGrade().getName())
                .build();
    }
}
