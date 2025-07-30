package com.eduvod.eduvod.service.schooladmin;

import com.eduvod.eduvod.dto.request.schooladmin.SubjectRequest;
import com.eduvod.eduvod.dto.response.common.BaseApiResponse;
import com.eduvod.eduvod.dto.response.schooladmin.SubjectResponse;
import com.eduvod.eduvod.model.schooladmin.Subject;
import com.eduvod.eduvod.model.superadmin.School;

import java.util.List;

public interface SubjectService {
    BaseApiResponse<SubjectResponse> createSubject(SubjectRequest request);
    BaseApiResponse<SubjectResponse> updateSubject(Long id, SubjectRequest request);
    BaseApiResponse<Void> deleteSubject(Long id);
//    BaseApiResponse<List<SubjectResponse>> getAllSubjects();
    BaseApiResponse<List<SubjectResponse>> getAllSubjectsForSchool();


}
