package com.eduvod.eduvod.service.schooladmin;

import com.eduvod.eduvod.dto.request.schooladmin.TeacherRequest;
import com.eduvod.eduvod.dto.response.BaseApiResponse;
import com.eduvod.eduvod.dto.response.schooladmin.TeacherResponse;

import java.util.List;

public interface TeacherService {
    BaseApiResponse<TeacherResponse> createTeacher(TeacherRequest request);
    BaseApiResponse<TeacherResponse> updateTeacher(Long id, TeacherRequest request);
    BaseApiResponse<Void> disableTeacher(Long id);
    BaseApiResponse<List<TeacherResponse>> getAllTeachers();
}
