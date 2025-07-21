package com.eduvod.eduvod.controller.schooladmin;

import com.eduvod.eduvod.dto.response.common.BaseApiResponse;
import com.eduvod.eduvod.dto.response.superadmin.GradeResponse;
import com.eduvod.eduvod.service.superadmin.GradeService;
import io.swagger.v3.oas.annotations.Operation;
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
}
