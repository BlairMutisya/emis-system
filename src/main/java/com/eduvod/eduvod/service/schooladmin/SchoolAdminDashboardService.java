package com.eduvod.eduvod.service.schooladmin;

import com.eduvod.eduvod.dto.response.BaseApiResponse;
import com.eduvod.eduvod.dto.response.schooladmin.SchoolAdminDashboardResponse;

public interface SchoolAdminDashboardService {
    BaseApiResponse<SchoolAdminDashboardResponse> getDashboardStats();
}
