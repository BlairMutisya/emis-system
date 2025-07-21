package com.eduvod.eduvod.controller.schooladmin;

import com.eduvod.eduvod.dto.request.schooladmin.StaffRequest;
import com.eduvod.eduvod.dto.response.common.BaseApiResponse;
import com.eduvod.eduvod.dto.response.schooladmin.StaffResponse;
import com.eduvod.eduvod.service.schooladmin.StaffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schooladmin/staff")
@RequiredArgsConstructor
@Tag(name = "Staff Management", description = "Manage School Staff")
public class StaffController {

    private final StaffService staffService;

    @PostMapping
    @Operation(summary = "Add new staff")
    public BaseApiResponse<StaffResponse> createStaff(@RequestBody StaffRequest request) {
        return staffService.createStaff(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update existing staff")
    public BaseApiResponse<StaffResponse> updateStaff(@PathVariable Long id, @RequestBody StaffRequest request) {
        return staffService.updateStaff(id, request);
    }

    @PutMapping("/{id}/disable")
    @Operation(summary = "Disable staff member")
    public BaseApiResponse<Void> disableStaff(@PathVariable Long id) {
        return staffService.disableStaff(id);
    }

    @GetMapping
    @Operation(summary = "Get all staff for current school")
    public BaseApiResponse<List<StaffResponse>> getAllStaff() {
        return staffService.getAllStaff();
    }
}
