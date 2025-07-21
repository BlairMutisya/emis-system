package com.eduvod.eduvod.service.superadmin;

import com.eduvod.eduvod.dto.request.superadmin.SubCountyRequest;
import com.eduvod.eduvod.dto.response.common.BaseApiResponse;
import com.eduvod.eduvod.dto.response.superadmin.SubCountyResponse;

import java.util.List;

public interface SubCountyService {
    BaseApiResponse<SubCountyResponse> createSubCounty(SubCountyRequest request);
    BaseApiResponse<SubCountyResponse> updateSubCounty(Long id, SubCountyRequest request);
    BaseApiResponse<String> deleteSubCounty(Long id);
    BaseApiResponse<SubCountyResponse> getBySubCountyId(Long id);
    BaseApiResponse<List<SubCountyResponse>> getAllSubCounties();
    BaseApiResponse<List<SubCountyResponse>> getByCountyId(Long countyId);
}
