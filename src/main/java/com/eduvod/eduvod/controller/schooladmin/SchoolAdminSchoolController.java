package com.eduvod.eduvod.controller.schooladmin;

import com.eduvod.eduvod.dto.response.BaseApiResponse;
import com.eduvod.eduvod.dto.response.schooladmin.SchoolResponse;
import com.eduvod.eduvod.service.schooladmin.SchoolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/schooladmin/school")
@RequiredArgsConstructor
@Tag(name = "School Admin - School", description = "Endpoints for managing assigned school information")
public class SchoolAdminSchoolController {

    private final SchoolService schoolService;

    @Operation(
            summary = "Get assigned school information",
            description = "Returns the school profile assigned to the currently logged-in school admin"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "School info retrieved successfully",
                    content = @Content(schema = @Schema(implementation = SchoolResponse.class))
            )
    })
    @GetMapping
    public ResponseEntity<BaseApiResponse<SchoolResponse>> getAssignedSchool() {
        SchoolResponse response = schoolService.getSchoolForLoggedInAdmin();
        return ResponseEntity.ok(new BaseApiResponse<>(200, "School info fetched", response));
    }

    @Operation(
            summary = "Update school logo",
            description = "Allows School Admin to upload a new school logo (image file only)"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "School logo updated successfully",
                    content = @Content(schema = @Schema(implementation = SchoolResponse.class))
            )
    })
    @PutMapping("/logo")
    public ResponseEntity<BaseApiResponse<SchoolResponse>> updateLogo(
            @RequestParam("file") MultipartFile file
    ) {
        SchoolResponse response = schoolService.updateSchoolLogo(file);
        return ResponseEntity.ok(new BaseApiResponse<>(200, "School logo updated", response));
    }
}
