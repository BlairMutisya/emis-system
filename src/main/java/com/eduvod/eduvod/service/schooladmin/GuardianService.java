package com.eduvod.eduvod.service.schooladmin;

import com.eduvod.eduvod.dto.request.schooladmin.GuardianRequest;
import com.eduvod.eduvod.dto.response.schooladmin.GuardianResponse;
import com.eduvod.eduvod.dto.response.common.BaseApiResponse;

import java.util.List;

public interface GuardianService {
    BaseApiResponse<GuardianResponse> createGuardian(GuardianRequest request);
    BaseApiResponse<GuardianResponse> updateGuardian(Long id, GuardianRequest request);
    BaseApiResponse<Void> deleteGuardian(Long id);
    BaseApiResponse<List<GuardianResponse>> getAllGuardians();
}