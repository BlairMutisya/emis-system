package com.eduvod.eduvod.controller.schooladmin;

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
@RequestMapping("/api/v1/schooladmin/grades")
@RequiredArgsConstructor
@Tag(name = "Grades-Management", description = "View grades based on curriculum")
public class SchoolAdminGradeController {

    private final GradeService gradeService;

    @Operation(summary = "Get grades by curriculum ID")
    @GetMapping("/by-curriculum/{curriculumId}")
    public BaseApiResponse<List<GradeResponse>> getGradesByCurriculum(@PathVariable Long curriculumId) {
        return gradeService.getGradesByCurriculum(curriculumId);
    }
    @Operation(
            summary = "Get grades for a school based on its curriculum",
            description = "Fetches a list of grades associated with a given school, based on the curriculum assigned to that school."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grades retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GradeResponse.class))),
            @ApiResponse(responseCode = "404", description = "School or curriculum not found",
                    content = @Content)
    })
    @GetMapping("/by-school/{schoolId}")
    public BaseApiResponse<List<GradeResponse>> getGradesForSchool(@PathVariable Long schoolId) {
        return gradeService.getGradesForSchool(schoolId);
    }
}
