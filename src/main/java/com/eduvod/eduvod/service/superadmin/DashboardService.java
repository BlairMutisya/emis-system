package com.eduvod.eduvod.service.superadmin;

import com.eduvod.eduvod.dto.response.common.BaseApiResponse;
import com.eduvod.eduvod.dto.response.superadmin.DashboardResponse;

public interface DashboardService {
    BaseApiResponse<DashboardResponse> getDashboardStats();
}
