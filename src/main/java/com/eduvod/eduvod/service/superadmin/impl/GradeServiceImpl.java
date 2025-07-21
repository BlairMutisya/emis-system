package com.eduvod.eduvod.service.superadmin.impl;

import com.eduvod.eduvod.dto.request.superadmin.GradeRequest;
import com.eduvod.eduvod.dto.response.common.BaseApiResponse;
import com.eduvod.eduvod.dto.response.superadmin.GradeResponse;
import com.eduvod.eduvod.model.superadmin.CurriculumType;
import com.eduvod.eduvod.model.superadmin.Grade;
import com.eduvod.eduvod.repository.superadmin.CurriculumTypeRepository;
import com.eduvod.eduvod.repository.superadmin.GradeRepository;
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

    @Override
    public BaseApiResponse<GradeResponse> createGrade(GradeRequest request) {
        if (gradeRepository.existsByName(request.getName())) {
            throw new RuntimeException("Grade with this name already exists.");
        }

        CurriculumType curriculum = curriculumTypeRepository.findById(request.getCurriculumId())
                .orElseThrow(() -> new RuntimeException("Curriculum not found."));

        Grade grade = Grade.builder()
                .name(request.getName())
                .curriculum(curriculum)
                .build();

        gradeRepository.save(grade);

        return BaseApiResponse.success(toResponse(grade));
    }

    @Override
    public BaseApiResponse<List<GradeResponse>> getAllGrades() {
        List<GradeResponse> responses = gradeRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
        return BaseApiResponse.success(responses);
    }

    @Override
    @Transactional
    public BaseApiResponse<String> deleteGrade(Long gradeId) {
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new RuntimeException("Grade not found"));

        gradeRepository.delete(grade);
        return BaseApiResponse.success("Grade deleted successfully");
    }

    @Override
    public BaseApiResponse<List<GradeResponse>> getGradesByCurriculum(Long curriculumId) {
        List<Grade> grades = gradeRepository.findByCurriculumId(curriculumId);
        List<GradeResponse> responses = grades.stream()
                .map(this::toResponse)
                .toList();
        return BaseApiResponse.success(responses);
    }

    private GradeResponse toResponse(Grade grade) {
        return GradeResponse.builder()
                .id(grade.getId())
                .name(grade.getName())
                .curriculum(grade.getCurriculum().getName())
                .build();
    }
}
