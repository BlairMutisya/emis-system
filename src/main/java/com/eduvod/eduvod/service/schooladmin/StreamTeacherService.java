package com.eduvod.eduvod.service.schooladmin;

import com.eduvod.eduvod.dto.request.schooladmin.AssignSubjectToStreamTeacherRequest;
import com.eduvod.eduvod.dto.request.schooladmin.AssignTeacherToStreamRequest;
import com.eduvod.eduvod.dto.response.BaseApiResponse;
import com.eduvod.eduvod.dto.response.schooladmin.StreamTeacherResponse;

import java.util.List;

public interface StreamTeacherService {
    BaseApiResponse<StreamTeacherResponse> assignTeacherToStream(AssignTeacherToStreamRequest request);
    BaseApiResponse<String> assignSubjectsToStreamTeacher(AssignSubjectToStreamTeacherRequest request);
    BaseApiResponse<List<StreamTeacherResponse>> getTeachersByStream(Long streamId);
}

