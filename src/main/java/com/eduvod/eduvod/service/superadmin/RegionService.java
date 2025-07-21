package com.eduvod.eduvod.service.superadmin;

import com.eduvod.eduvod.dto.request.superadmin.RegionRequest;
import com.eduvod.eduvod.dto.response.common.BaseApiResponse;
import com.eduvod.eduvod.dto.response.superadmin.CountyResponse;
import com.eduvod.eduvod.dto.response.superadmin.RegionResponse;

import java.util.List;

public interface RegionService {
    BaseApiResponse<RegionResponse> createRegion(RegionRequest request);
    BaseApiResponse<RegionResponse> updateRegion(Long id, RegionRequest request);
    BaseApiResponse<String>deleteRegion(Long id);
    BaseApiResponse<RegionResponse> getRegionById(Long id);
    BaseApiResponse<List<RegionResponse>> getAllRegions();
    BaseApiResponse<List<CountyResponse>> getCountiesByRegionId(Long regionId);
}
