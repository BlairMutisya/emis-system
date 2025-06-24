package com.eduvod.eduvod.service.superadmin;

import com.eduvod.eduvod.dto.request.superadmin.GradeRequest;
import com.eduvod.eduvod.dto.response.BaseApiResponse;
import com.eduvod.eduvod.dto.response.superadmin.GradeResponse;

import java.util.List;

public interface GradeService {
    BaseApiResponse<GradeResponse> createGrade(GradeRequest request);
    BaseApiResponse<List<GradeResponse>> getAllGrades();
    BaseApiResponse<String> deleteGrade(Long gradeId);
}
