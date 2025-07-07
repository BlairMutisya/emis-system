package com.eduvod.eduvod.controller.superadmin;

import com.eduvod.eduvod.dto.request.superadmin.SchoolRequest;
import com.eduvod.eduvod.dto.response.BaseApiResponse;
import com.eduvod.eduvod.dto.response.superadmin.SchoolResponse;
import com.eduvod.eduvod.service.superadmin.SchoolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/superadmin/schools")
@RequiredArgsConstructor
@Tag(name = "School Management", description = "Super Admin - School CRUD and import")
public class SuperAdminSchoolController {

    private final SchoolService schoolService;

    @Operation(
            summary = "Create new school",
            description = "Creates a new school with details such as name, type, category, etc.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "School created successfully",
                            content = @Content(schema = @Schema(implementation = SchoolResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request payload")
            }
    )
    @PostMapping
    public ResponseEntity<BaseApiResponse<SchoolResponse>> create(@RequestBody SchoolRequest request) {
        SchoolResponse response = schoolService.createSchool(request);
        return ResponseEntity.ok(BaseApiResponse.<SchoolResponse>builder()
                .statusCode(200)
                .message("School created successfully")
                .data(response)
                .build());
    }

    @Operation(
            summary = "Get all schools",
            description = "Retrieves all schools registered in the system",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Fetched schools successfully",
                            content = @Content(schema = @Schema(implementation = SchoolResponse.class)))
            }
    )
    @GetMapping
    public ResponseEntity<BaseApiResponse<List<SchoolResponse>>> getAll() {
        List<SchoolResponse> schools = schoolService.getAllSchools();
        return ResponseEntity.ok(BaseApiResponse.<List<SchoolResponse>>builder()
                .statusCode(200)
                .message("Fetched schools successfully")
                .data(schools)
                .build());
    }

    @Operation(
            summary = "Download school import template",
            description = "Provides an Excel/CSV file template for importing school data",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Template file downloaded")
            }
    )
    @GetMapping("/import/template")
    public ResponseEntity<Resource> downloadTemplate() {
        Resource resource = schoolService.getImportTemplate();
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=school_import_template.xlsx")
                .body(resource);
    }

    @Operation(
            summary = "Import schools from template",
            description = "Uploads an Excel/CSV file to bulk create schools",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Schools imported successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid file format or parsing error")
            }
    )
    @PostMapping("/import")
    public ResponseEntity<BaseApiResponse<String>> importSchools(@RequestParam("file") MultipartFile file) {
        schoolService.importSchools(file);
        return ResponseEntity.ok(BaseApiResponse.<String>builder()
                .statusCode(200)
                .message("Schools imported successfully")
                .data("Import completed")
                .build());
    }
}
