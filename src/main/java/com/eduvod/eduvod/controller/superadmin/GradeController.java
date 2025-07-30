package com.eduvod.eduvod.controller.superadmin;

import com.eduvod.eduvod.dto.request.superadmin.GradeRequest;
import com.eduvod.eduvod.dto.response.common.BaseApiResponse;
import com.eduvod.eduvod.dto.response.superadmin.GradeResponse;
import com.eduvod.eduvod.service.superadmin.GradeService;
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
@RequestMapping("/api/v1/superadmin/grades")
@RequiredArgsConstructor
@Tag(name = "Super Admin - Grade Management", description = "Manage grade creation and deletion")
public class GradeController {

    private final GradeService gradeService;

    @Operation(summary = "Create a new grade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grade created successfully",
                    content = @Content(schema = @Schema(implementation = GradeResponse.class)))
    })

    @PostMapping
    public BaseApiResponse<GradeResponse> createGrade(@RequestBody GradeRequest request) {
        return gradeService.createGrade(request);
    }


    @Operation(summary = "Get all grades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of grades retrieved successfully",
                    content = @Content(schema = @Schema(implementation = GradeResponse.class)))
    })
    @GetMapping
    public BaseApiResponse<List<GradeResponse>> getAllGrades() {
        return gradeService.getAllGrades();
    }

    @Operation(summary = "Delete a grade by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grade deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Grade not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public BaseApiResponse<String> deleteGrade(@PathVariable Long id) {
        return gradeService.deleteGrade(id);
    }
}
