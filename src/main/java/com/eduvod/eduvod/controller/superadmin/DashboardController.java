package com.eduvod.eduvod.controller.superadmin;

import com.eduvod.eduvod.dto.response.common.BaseApiResponse;
import com.eduvod.eduvod.dto.response.superadmin.DashboardResponse;
import com.eduvod.eduvod.service.superadmin.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/superadmin/dashboard")
@RequiredArgsConstructor
@Tag(name = "Super Admin - Dashboard", description = "Provides dashboard analytics for Super Admins")
public class DashboardController {

    private final DashboardService dashboardService;

    @Operation(summary = "Fetch Super Admin dashboard statistics")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dashboard data retrieved successfully",
                    content = @Content(schema = @Schema(implementation = DashboardResponse.class)))
    })
    @GetMapping
    public BaseApiResponse<DashboardResponse> getDashboard() {
        return dashboardService.getDashboardStats();
    }
}
