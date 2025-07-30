package com.eduvod.eduvod.service.superadmin.impl;

import com.eduvod.eduvod.constants.ErrorMessages;
import com.eduvod.eduvod.dto.request.superadmin.GradeRequest;
import com.eduvod.eduvod.dto.response.common.BaseApiResponse;
import com.eduvod.eduvod.dto.response.superadmin.GradeResponse;
import com.eduvod.eduvod.exception.DuplicateResourceException;
import com.eduvod.eduvod.exception.ResourceNotFoundException;
import com.eduvod.eduvod.model.superadmin.CurriculumType;
import com.eduvod.eduvod.model.superadmin.Grade;
import com.eduvod.eduvod.model.superadmin.School;
import com.eduvod.eduvod.repository.superadmin.CurriculumTypeRepository;
import com.eduvod.eduvod.repository.superadmin.GradeRepository;
import com.eduvod.eduvod.repository.superadmin.SchoolRepository;
import com.eduvod.eduvod.service.superadmin.GradeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final CurriculumTypeRepository curriculumTypeRepository;
    private final SchoolRepository schoolRepository;

    @Override
    public BaseApiResponse<GradeResponse> createGrade(GradeRequest request) {
        if (gradeRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException(ErrorMessages.GRADE_ALREADY_EXISTS);
        }

        CurriculumType curriculum = curriculumTypeRepository.findById(request.getCurriculumId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.CURRICULUM_NOT_FOUND));

        Grade grade = Grade.builder()
                .name(request.getName())
                .curriculum(curriculum)
                .build();

        gradeRepository.save(grade);

        return new BaseApiResponse<>(
                ErrorMessages.STATUS_CREATED,
                ErrorMessages.GRADE_CREATED,
                toResponse(grade)
        );
    }

    @Override
    public BaseApiResponse<List<GradeResponse>> getAllGrades() {
        List<GradeResponse> responses = gradeRepository.findAll().stream()
                .map(this::toResponse)
                .toList();

        return new BaseApiResponse<>(
                ErrorMessages.STATUS_OK,
                ErrorMessages.ALL_GRADES_FETCHED,
                responses
        );
    }

    @Override
    @Transactional
    public BaseApiResponse<String> deleteGrade(Long gradeId) {
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.GRADE_NOT_FOUND));

        gradeRepository.delete(grade);

        return new BaseApiResponse<>(
                ErrorMessages.STATUS_OK,
                ErrorMessages.GRADE_DELETED,
                "Grade deleted successfully"
        );
    }
    @Override
    public BaseApiResponse<List<GradeResponse>> getGradesForSchool(Long schoolId) {
        School school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.SCHOOL_NOT_FOUND));

        CurriculumType curriculum = school.getCurriculum();

        if (curriculum == null) {
            throw new ResourceNotFoundException("Curriculum not linked to this school");
        }

        List<Grade> grades = gradeRepository.findByCurriculumId(curriculum.getId());

        List<GradeResponse> responses = grades.stream()
                .map(this::toResponse)
                .toList();

        return new BaseApiResponse<>(
                ErrorMessages.STATUS_OK,
                "Grades for school fetched successfully",
                responses
        );
    }


    @Override
    public BaseApiResponse<List<GradeResponse>> getGradesByCurriculum(Long curriculumId) {
        CurriculumType curriculum = curriculumTypeRepository.findById(curriculumId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.CURRICULUM_NOT_FOUND));

        List<GradeResponse> responses = gradeRepository.findByCurriculumId(curriculumId).stream()
                .map(this::toResponse)
                .toList();

        return new BaseApiResponse<>(
                ErrorMessages.STATUS_OK,
                ErrorMessages.GRADES_BY_CURRICULUM_FETCHED,
                responses
        );
    }

    private GradeResponse toResponse(Grade grade) {
        return GradeResponse.builder()
                .id(grade.getId())
                .name(grade.getName())
                .curriculum(grade.getCurriculum().getName())
                .build();
    }
}
