package com.eduvod.eduvod.controller.superadmin;

import com.eduvod.eduvod.dto.response.BaseApiResponse;
import com.eduvod.eduvod.dto.response.superadmin.DashboardResponse;
import com.eduvod.eduvod.service.superadmin.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/superadmin/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public BaseApiResponse<DashboardResponse> getDashboard() {
        return dashboardService.getDashboardStats();
    }
}
