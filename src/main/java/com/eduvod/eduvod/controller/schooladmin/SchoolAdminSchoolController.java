package com.eduvod.eduvod.controller.schooladmin;

import com.eduvod.eduvod.dto.response.BaseApiResponse;
import com.eduvod.eduvod.dto.response.schooladmin.SchoolResponse;
import com.eduvod.eduvod.service.schooladmin.SchoolService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/schooladmin/school")
@RequiredArgsConstructor
public class SchoolAdminSchoolController {

    private final SchoolService schoolService;

    @Operation(summary = "Get assigned school information")
    @GetMapping
    public ResponseEntity<BaseApiResponse<SchoolResponse>> getAssignedSchool() {
        SchoolResponse response = schoolService.getSchoolForLoggedInAdmin();
        return ResponseEntity.ok(new BaseApiResponse<>(200, "School info fetched", response));
    }

    @Operation(summary = "Update school logo", description = "Allows School Admin to upload a new logo image")
    @PutMapping("/logo")
    public ResponseEntity<BaseApiResponse<SchoolResponse>> updateLogo(
            @RequestParam("file") MultipartFile file
    ) {
        SchoolResponse response = schoolService.updateSchoolLogo(file);
        return ResponseEntity.ok(new BaseApiResponse<>(200, "School logo updated", response));
    }
}
