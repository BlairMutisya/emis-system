package com.eduvod.eduvod.controller.superadmin;

import com.eduvod.eduvod.dto.request.superadmin.AssignSchoolRequest;
import com.eduvod.eduvod.dto.request.superadmin.SchoolAdminRequest;
import com.eduvod.eduvod.dto.request.superadmin.UpdateSchoolAdminPasswordRequest;
import com.eduvod.eduvod.dto.response.superadmin.SchoolAdminResponse;
import com.eduvod.eduvod.enums.UserStatus;
import com.eduvod.eduvod.service.superadmin.SchoolAdminService;
import com.eduvod.eduvod.dto.response.BaseApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/superadmin/school-admins")
@RequiredArgsConstructor
@Tag(name = "Super Admin - School Admin Management", description = "Manage school admin creation, status, and assignment")
public class SchoolAdminController {

    private final SchoolAdminService schoolAdminService;

    @Operation(summary = "Create a new school admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "School admin created successfully",
                    content = @Content(schema = @Schema(implementation = SchoolAdminResponse.class)))
    })
    @PostMapping
    public ResponseEntity<BaseApiResponse<SchoolAdminResponse>> createSchoolAdmin(
            @RequestBody SchoolAdminRequest request) {
        SchoolAdminResponse response = schoolAdminService.createSchoolAdmin(request);
        return ResponseEntity.ok(new BaseApiResponse<>(201, "School admin created successfully", response));
    }

    @Operation(summary = "Get all school admins")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched all school admins",
                    content = @Content(schema = @Schema(implementation = SchoolAdminResponse.class)))
    })
    @GetMapping
    public ResponseEntity<BaseApiResponse<List<SchoolAdminResponse>>> getAllSchoolAdmins() {
        List<SchoolAdminResponse> admins = schoolAdminService.getAllSchoolAdmins();
        return ResponseEntity.ok(new BaseApiResponse<>(200, "Fetched school admins", admins));
    }

    @Operation(summary = "Assign a school to a school admin")
    @PutMapping("/assign-school")
    public ResponseEntity<BaseApiResponse<Void>> assignSchool(
            @RequestBody AssignSchoolRequest request
    ) {
        schoolAdminService.assignSchool(request);
        return ResponseEntity.ok(new BaseApiResponse<>(200, "School assigned successfully"));
    }

    @Operation(summary = "Update school admin status (ACTIVE/BLOCKED)")
    @PutMapping("/{id}/status")
    public ResponseEntity<BaseApiResponse<Void>> updateStatus(
            @Parameter(description = "School admin ID") @PathVariable Long id,
            @Parameter(description = "New status: ACTIVE or BLOCKED") @RequestParam UserStatus status
    ) {
        schoolAdminService.updateStatus(id, status);
        return ResponseEntity.ok(new BaseApiResponse<>(200, "School admin status updated"));
    }

    @Operation(summary = "Update school admin password")
    @PutMapping("/{id}/password")
    public ResponseEntity<BaseApiResponse<Void>> updatePassword(
            @Parameter(description = "School admin ID") @PathVariable Long id,
            @RequestBody UpdateSchoolAdminPasswordRequest request
    ) {
        schoolAdminService.updatePassword(id, request);
        return ResponseEntity.ok(new BaseApiResponse<>(200, "Password updated successfully"));
    }

    @Operation(summary = "Soft delete school admin")
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseApiResponse<Void>> softDeleteSchoolAdmin(
            @Parameter(description = "School admin ID") @PathVariable Long id) {
        schoolAdminService.softDelete(id);
        return ResponseEntity.ok(new BaseApiResponse<>(200, "School admin deleted successfully"));
    }

    @PutMapping("/unassign-school/{schoolAdminId}")
    @Operation(summary = "Unassign school from a school admin")
    public ResponseEntity<BaseApiResponse<String>> unassignSchool(@PathVariable Long schoolAdminId) {
        return ResponseEntity.ok(schoolAdminService.unassignSchool(schoolAdminId));
    }


}
