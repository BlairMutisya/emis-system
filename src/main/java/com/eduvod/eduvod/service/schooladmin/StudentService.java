package com.eduvod.eduvod.service.schooladmin;

import com.eduvod.eduvod.dto.request.schooladmin.StudentRequest;
import com.eduvod.eduvod.dto.response.BaseApiResponse;
import com.eduvod.eduvod.dto.response.schooladmin.StudentResponse;
import java.util.List;

public interface StudentService {
    BaseApiResponse<StudentResponse> createStudent(Long streamId, StudentRequest request);
    BaseApiResponse<StudentResponse> updateStudent(Long studentId, StudentRequest request);
    BaseApiResponse<StudentResponse> disableStudent(Long studentId);
    BaseApiResponse<StudentResponse> getStudentById(Long id);
    BaseApiResponse<List<StudentResponse>> getStudentsByStreamId(Long streamId);
    }
