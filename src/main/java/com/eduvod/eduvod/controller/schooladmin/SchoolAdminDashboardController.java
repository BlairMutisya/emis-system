package com.eduvod.eduvod.controller.schooladmin;

import com.eduvod.eduvod.dto.response.common.BaseApiResponse;
import com.eduvod.eduvod.dto.response.schooladmin.SchoolAdminDashboardResponse;
import com.eduvod.eduvod.service.schooladmin.SchoolAdminDashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/schooladmin/dashboard")
@RequiredArgsConstructor
@Tag(name = "School Admin Dashboard", description = "Dashboard statistics for School Admin")
public class SchoolAdminDashboardController {
    private final SchoolAdminDashboardService dashboardService;
//    @PostConstruct
//    public void init() {
//        System.out.println("SchoolAdminDashboardController loaded");
//    }


    @Operation(
            summary = "Get school admin dashboard statistics",
            description = "Retrieves student, teacher, guardian, and stream statistics for the current school"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Dashboard statistics retrieved successfully",
                    content = @Content(schema = @Schema(implementation = SchoolAdminDashboardResponse.class))
            )
    })
    @GetMapping
    public BaseApiResponse<SchoolAdminDashboardResponse> getDashboardStats() {
        return dashboardService.getDashboardStats();
    }
}
