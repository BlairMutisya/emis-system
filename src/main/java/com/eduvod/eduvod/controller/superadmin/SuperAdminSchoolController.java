package com.eduvod.eduvod.controller.superadmin;

import com.eduvod.eduvod.dto.request.superadmin.SchoolRequest;
import com.eduvod.eduvod.dto.response.common.BaseApiResponse;
import com.eduvod.eduvod.dto.response.common.PagedResponse;
import com.eduvod.eduvod.dto.response.superadmin.SchoolResponse;
import com.eduvod.eduvod.service.superadmin.SchoolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
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

    @PostMapping
    @Operation(summary = "Create new school", description = "Creates a new school with full details")
    public ResponseEntity<BaseApiResponse<SchoolResponse>> create(@RequestBody SchoolRequest request) {
        SchoolResponse response = schoolService.createSchool(request);
        return ResponseEntity.ok(BaseApiResponse.success("School created successfully", response));
    }

    @GetMapping
    @Operation(
            summary = "Get schools (paginated or all)",
            description = "Returns all schools if no pagination is provided, or paginated results if `page` and `size` are specified"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Schools fetched successfully")
    })
    public ResponseEntity<BaseApiResponse<?>> getSchools(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        if (page != null && size != null) {
            PagedResponse<SchoolResponse> paged = schoolService.getAllSchools(page, size);
            return ResponseEntity.ok(BaseApiResponse.success("Paginated schools fetched successfully", paged));
        }

        List<SchoolResponse> all = schoolService.getAllSchools();
        return ResponseEntity.ok(BaseApiResponse.success("All schools fetched successfully", all));
    }

    @GetMapping("/template-download")
    @Operation(summary = "Download school import template", description = "Provides an Excel file template for importing schools")
    public ResponseEntity<Resource> exportSchools() {
        Resource file = schoolService.exportSchools();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=schools_export.xlsx")
                .body(file);
    }

    @PostMapping("/template-import")
    @Operation(summary = "Import schools", description = "Uploads an Excel/CSV file to bulk create schools")
    public ResponseEntity<BaseApiResponse<String>> importSchools(@RequestParam("file") MultipartFile file) {
        schoolService.importSchools(file);
        return ResponseEntity.ok(BaseApiResponse.success("Schools imported successfully", "Import completed"));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a school", description = "Updates the details of a specific school by ID")
    public ResponseEntity<BaseApiResponse<SchoolResponse>> updateSchool(
            @PathVariable Long id,
            @RequestBody SchoolRequest request
    ) {
        request.setId(id);
        return ResponseEntity.ok(schoolService.updateSchool(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get school by ID", description = "Fetches a single school by its ID")
    public ResponseEntity<BaseApiResponse<SchoolResponse>> getSchoolById(@PathVariable Long id) {
        return ResponseEntity.ok(schoolService.getSchoolById(id));
    }
}
