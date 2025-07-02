package com.eduvod.eduvod.controller.schooladmin;

import com.eduvod.eduvod.dto.request.schooladmin.StudentRequest;
import com.eduvod.eduvod.dto.response.BaseApiResponse;
import com.eduvod.eduvod.dto.response.schooladmin.StudentResponse;
import com.eduvod.eduvod.service.schooladmin.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/school-admin/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    // Create student
    @PostMapping("/stream/{streamId}")
    public BaseApiResponse<StudentResponse> createStudent(
            @PathVariable Long streamId,
            @RequestBody StudentRequest request
    ) {
        return studentService.createStudent(streamId, request);
    }

    // Update student
    @PutMapping("/{studentId}")
    public BaseApiResponse<StudentResponse> updateStudent(
            @PathVariable Long studentId,
            @RequestBody StudentRequest request
    ) {
        return studentService.updateStudent(studentId, request);
    }

    // Disable student
    @PutMapping("/{studentId}/disable")
    public BaseApiResponse<StudentResponse> disableStudent(
            @PathVariable Long studentId
    ) {
        return studentService.disableStudent(studentId);
    }

    // Get student by ID
    @GetMapping("/{studentId}")
    public BaseApiResponse<StudentResponse> getStudentById(
            @PathVariable Long studentId
    ) {
        return studentService.getStudentById(studentId);
    }

    // Get all students by stream
    @GetMapping("/by-stream/{streamId}")
    public BaseApiResponse<List<StudentResponse>> getStudentsByStream(
            @PathVariable Long streamId
    ) {
        return studentService.getStudentsByStreamId(streamId);
    }
}
