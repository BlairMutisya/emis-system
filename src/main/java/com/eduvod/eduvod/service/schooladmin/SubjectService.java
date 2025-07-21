package com.eduvod.eduvod.service.schooladmin;

import com.eduvod.eduvod.dto.request.schooladmin.SubjectRequest;
import com.eduvod.eduvod.dto.response.common.BaseApiResponse;
import com.eduvod.eduvod.dto.response.schooladmin.SubjectResponse;

import java.util.List;

public interface SubjectService {
    BaseApiResponse<SubjectResponse> createSubject(SubjectRequest request);
    BaseApiResponse<SubjectResponse> updateSubject(Long id, SubjectRequest request);
    BaseApiResponse<Void> deleteSubject(Long id);
    BaseApiResponse<List<SubjectResponse>> getAllSubjects();
}
