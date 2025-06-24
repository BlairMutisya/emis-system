package com.eduvod.eduvod.controller.superadmin;

import com.eduvod.eduvod.dto.request.superadmin.GradeRequest;
import com.eduvod.eduvod.dto.response.BaseApiResponse;
import com.eduvod.eduvod.dto.response.superadmin.GradeResponse;
import com.eduvod.eduvod.service.superadmin.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/superadmin/grades")
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;

    @PostMapping
    public BaseApiResponse<GradeResponse> createGrade(@RequestBody GradeRequest request) {
        return gradeService.createGrade(request);
    }

    @GetMapping
    public BaseApiResponse<List<GradeResponse>> getAllGrades() {
        return gradeService.getAllGrades();
    }

    @DeleteMapping("/{id}")
    public BaseApiResponse<String> deleteGrade(@PathVariable Long id) {
        return gradeService.deleteGrade(id);
    }
}
