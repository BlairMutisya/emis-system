package com.eduvod.eduvod.controller.schooladmin;

import com.eduvod.eduvod.dto.request.schooladmin.StudentRequest;
import com.eduvod.eduvod.dto.response.common.BaseApiResponse;
import com.eduvod.eduvod.dto.response.schooladmin.StudentResponse;
import com.eduvod.eduvod.service.schooladmin.StudentService;
import com.eduvod.eduvod.repository.schooladmin.StreamRepository;
import com.eduvod.eduvod.util.ExcelStudentTemplateUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/schooladmin/students")
@RequiredArgsConstructor
@Tag(name = "Student Management", description = "Endpoints for managing students")
public class StudentController {

    private final StudentService studentService;
    private final StreamRepository streamRepository;

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

    @Operation(summary = "Download a blank Excel student import template for the students")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Excel template downloaded")
    })
    @GetMapping("/{streamId}/download")
    public ResponseEntity<byte[]> downloadStudentTemplate(@PathVariable Long streamId) {
        try {
            // Validate if stream exists
            if (!streamRepository.existsById(streamId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null);
            }

            ByteArrayInputStream in = ExcelStudentTemplateUtil.generateTemplate();
            byte[] excelContent = in.readAllBytes();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(ContentDisposition.builder("attachment")
                    .filename("student_template_stream_" + streamId + ".xlsx")
                    .build());
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            return ResponseEntity.ok().headers(headers).body(excelContent);
        } catch (IOException e) {
            throw new RuntimeException("Error generating Excel template", e);
        }
    }
    @GetMapping("/export/stream/{streamId}")
    @Operation(
            summary = "Export students in Excel format",
            description = "Generates and downloads an Excel file with all students in the specified stream"
    )
    public ResponseEntity<InputStreamResource> exportStudentsToExcel(
            @Parameter(description = "ID of the stream to export students from") @PathVariable Long streamId,
            HttpServletResponse response
    ) {
        ByteArrayInputStream excelStream = studentService.exportAllStudentsToExcel(streamId);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=students_stream_" + streamId + ".xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(excelStream));
    }


    @Operation(summary = "Import students from a specific stream via Excel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Students imported successfully")
    })
    @PostMapping("/{streamId}/import")
    public ResponseEntity<BaseApiResponse<String>> importStudents(
            @PathVariable Long streamId,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            BaseApiResponse<String> response = studentService.importStudents(streamId, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new BaseApiResponse<>(400, "Failed to import students", e.getMessage()));
        }
    }
    @PutMapping("/{studentId}/assign-guardian/{guardianId}")
    @Operation(
            summary = "Assign a guardian to a student",
            description = "Assigns a guardian to the specified student by their IDs."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Guardian assigned successfully"),
            @ApiResponse(responseCode = "404", description = "Student or Guardian not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<BaseApiResponse<StudentResponse>> assignGuardian(
            @PathVariable Long studentId,
            @PathVariable Long guardianId) {

        BaseApiResponse<StudentResponse> response = studentService.assignGuardian(studentId, guardianId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

}
