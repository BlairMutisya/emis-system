package com.eduvod.eduvod.service.schooladmin;

import com.eduvod.eduvod.dto.request.schooladmin.StudentRequest;
import com.eduvod.eduvod.dto.response.common.BaseApiResponse;
import com.eduvod.eduvod.dto.response.schooladmin.StudentResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudentService {
    BaseApiResponse<StudentResponse> createStudent(Long streamId, StudentRequest request);
    BaseApiResponse<StudentResponse> updateStudent(Long studentId, StudentRequest request);
    BaseApiResponse<StudentResponse> disableStudent(Long studentId);
    BaseApiResponse<StudentResponse> getStudentById(Long id);
    BaseApiResponse<List<StudentResponse>> getStudentsByStreamId(Long streamId);
    BaseApiResponse<String> importStudents(Long streamId, MultipartFile file) throws Exception;
    BaseApiResponse<StudentResponse> assignGuardian(Long studentId, Long guardianId);

}
