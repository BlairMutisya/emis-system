package com.eduvod.eduvod.service.superadmin;

import com.eduvod.eduvod.dto.request.superadmin.GradeRequest;
import com.eduvod.eduvod.dto.response.common.BaseApiResponse;
import com.eduvod.eduvod.dto.response.superadmin.GradeResponse;

import java.util.List;

public interface GradeService {
    BaseApiResponse<GradeResponse> createGrade(GradeRequest request);
    BaseApiResponse<List<GradeResponse>> getAllGrades();
    BaseApiResponse<List<GradeResponse>> getGradesByCurriculum(Long curriculumId);

    BaseApiResponse<String> deleteGrade(Long gradeId);


}
