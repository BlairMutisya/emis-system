package com.eduvod.eduvod.controller.schooladmin;

import com.eduvod.eduvod.dto.request.schooladmin.StudentRequest;
import com.eduvod.eduvod.dto.response.BaseApiResponse;
import com.eduvod.eduvod.dto.response.schooladmin.StudentResponse;
import com.eduvod.eduvod.service.schooladmin.StudentService;
import com.eduvod.eduvod.util.ExcelStudentTemplateUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/schooladmin/students")
@RequiredArgsConstructor
@Tag(name = "School Admin - Student Management", description = "Endpoints for managing students")
public class StudentController {

    private final StudentService studentService;

    @Operation(summary = "Create a student under a specific stream")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student created successfully",
                    content = @Content(schema = @Schema(implementation = StudentResponse.class)))
    })
    @PostMapping("/stream/{streamId}")
    public BaseApiResponse<StudentResponse> createStudent(
            @PathVariable Long streamId,
            @RequestBody StudentRequest request
    ) {
        return studentService.createStudent(streamId, request);
    }

    @Operation(summary = "Update student details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student updated successfully",
                    content = @Content(schema = @Schema(implementation = StudentResponse.class)))
    })
    @PutMapping("/{studentId}")
    public BaseApiResponse<StudentResponse> updateStudent(
            @PathVariable Long studentId,
            @RequestBody StudentRequest request
    ) {
        return studentService.updateStudent(studentId, request);
    }

    @Operation(summary = "Disable a student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student disabled successfully",
                    content = @Content(schema = @Schema(implementation = StudentResponse.class)))
    })
    @PutMapping("/{studentId}/disable")
    public BaseApiResponse<StudentResponse> disableStudent(@PathVariable Long studentId) {
        return studentService.disableStudent(studentId);
    }

    @Operation(summary = "Get a student by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student fetched successfully",
                    content = @Content(schema = @Schema(implementation = StudentResponse.class)))
    })
    @GetMapping("/{studentId}")
    public BaseApiResponse<StudentResponse> getStudentById(@PathVariable Long studentId) {
        return studentService.getStudentById(studentId);
    }

    @Operation(summary = "Get all students by stream ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Students fetched successfully",
                    content = @Content(schema = @Schema(implementation = StudentResponse.class)))
    })
    @GetMapping("/by-stream/{streamId}")
    public BaseApiResponse<List<StudentResponse>> getStudentsByStream(@PathVariable Long streamId) {
        return studentService.getStudentsByStreamId(streamId);
    }

    @Operation(summary = "Download Excel student import template")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Excel template downloaded")
    })
    @GetMapping("/template/download")
    public ResponseEntity<byte[]> downloadStudentTemplate() {
        try {
            ByteArrayInputStream in = ExcelStudentTemplateUtil.generateTemplate();
            byte[] excelContent = in.readAllBytes();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(ContentDisposition.builder("attachment")
                    .filename("student_template.xlsx")
                    .build());
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            return ResponseEntity.ok().headers(headers).body(excelContent);
        } catch (IOException e) {
            throw new RuntimeException("Error generating Excel template", e);
        }
    }
}
