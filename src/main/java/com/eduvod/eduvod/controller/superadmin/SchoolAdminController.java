package com.eduvod.eduvod.controller.superadmin;

import com.eduvod.eduvod.dto.request.superadmin.AssignSchoolRequest;
import com.eduvod.eduvod.dto.request.superadmin.SchoolAdminRequest;
import com.eduvod.eduvod.dto.request.superadmin.UpdateSchoolAdminPasswordRequest;
import com.eduvod.eduvod.dto.response.superadmin.SchoolAdminResponse;
import com.eduvod.eduvod.enums.UserStatus;
import com.eduvod.eduvod.service.superadmin.SchoolAdminService;
import com.eduvod.eduvod.dto.response.BaseApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/superadmin/school-admins")
@RequiredArgsConstructor
public class SchoolAdminController {

    private final SchoolAdminService schoolAdminService;

    @PostMapping
    public ResponseEntity<BaseApiResponse<SchoolAdminResponse>> createSchoolAdmin(@RequestBody SchoolAdminRequest request) {
        SchoolAdminResponse response = schoolAdminService.createSchoolAdmin(request);
        return ResponseEntity.ok(new BaseApiResponse<>(201, "School admin created successfully", response));
    }

    @GetMapping
    public ResponseEntity<BaseApiResponse<List<SchoolAdminResponse>>> getAllSchoolAdmins() {
        List<SchoolAdminResponse> admins = schoolAdminService.getAllSchoolAdmins();
        return ResponseEntity.ok(new BaseApiResponse<>(200, "Fetched school admins", admins));
    }

    @PutMapping("/assign-school")
    public ResponseEntity<BaseApiResponse<Void>> assignSchool(
            @RequestBody AssignSchoolRequest request
    ) {
        schoolAdminService.assignSchool(request);
        return ResponseEntity.ok(new BaseApiResponse<>(200, "School assigned successfully"));
    }


    @PutMapping("/{id}/status")
    public ResponseEntity<BaseApiResponse<Void>> updateStatus(
            @PathVariable Long id,
            @RequestParam UserStatus status
    ) {
        schoolAdminService.updateStatus(id, status);
        return ResponseEntity.ok(new BaseApiResponse<>(200, "School admin status updated"));
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<BaseApiResponse<Void>> updatePassword(
            @PathVariable Long id,
            @RequestBody UpdateSchoolAdminPasswordRequest request
    ) {
        schoolAdminService.updatePassword(id, request);
        return ResponseEntity.ok(new BaseApiResponse<>(200, "Password updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseApiResponse<Void>> softDeleteSchoolAdmin(@PathVariable Long id) {
        schoolAdminService.softDelete(id);
        return ResponseEntity.ok(new BaseApiResponse<>(200, "School admin deleted successfully"));
    }
}
