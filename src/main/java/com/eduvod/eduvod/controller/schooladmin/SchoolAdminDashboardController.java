package com.eduvod.eduvod.controller.schooladmin;

import com.eduvod.eduvod.dto.response.BaseApiResponse;
import com.eduvod.eduvod.dto.response.schooladmin.SchoolAdminDashboardResponse;
import com.eduvod.eduvod.service.schooladmin.SchoolAdminDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/school-admin/dashboard")
@RequiredArgsConstructor
public class SchoolAdminDashboardController {

    private final SchoolAdminDashboardService dashboardService;

    @GetMapping
    public BaseApiResponse<SchoolAdminDashboardResponse> getDashboardStats() {
        return dashboardService.getDashboardStats();
    }
}
