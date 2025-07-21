package com.eduvod.eduvod.controller.schooladmin;

import com.eduvod.eduvod.dto.request.schooladmin.SubjectRequest;
import com.eduvod.eduvod.dto.response.common.BaseApiResponse;
import com.eduvod.eduvod.dto.response.schooladmin.SubjectResponse;
import com.eduvod.eduvod.service.schooladmin.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schooladmin/subjects")
@RequiredArgsConstructor
@Tag(name = "Subject Management", description = "Endpoints for managing school subjects")
public class SubjectController {

    private final SubjectService subjectService;

    @Operation(summary = "Create a new subject")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subject created successfully",
                    content = @Content(schema = @Schema(implementation = SubjectResponse.class)))
    })
    @PostMapping
    public BaseApiResponse<SubjectResponse> createSubject(
            @RequestBody SubjectRequest request) {
        return subjectService.createSubject(request);
    }

    @Operation(summary = "Update subject details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subject updated successfully",
                    content = @Content(schema = @Schema(implementation = SubjectResponse.class)))
    })
    @PutMapping("/{id}")
    public BaseApiResponse<SubjectResponse> updateSubject(
            @PathVariable Long id,
            @RequestBody SubjectRequest request) {
        return subjectService.updateSubject(id, request);
    }

    @Operation(summary = "Delete a subject")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subject deleted successfully")
    })
    @DeleteMapping("/{id}")
    public BaseApiResponse<Void> deleteSubject(@PathVariable Long id) {
        return subjectService.deleteSubject(id);
    }

    @Operation(summary = "Get all subjects for the school")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subjects retrieved successfully",
                    content = @Content(schema = @Schema(implementation = SubjectResponse.class)))
    })
    @GetMapping
    public BaseApiResponse<List<SubjectResponse>> getAllSubjects() {
        return subjectService.getAllSubjects();
    }
}
