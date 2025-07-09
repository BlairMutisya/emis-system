package com.eduvod.eduvod.service.superadmin;

import com.eduvod.eduvod.dto.request.superadmin.CountyRequest;
import com.eduvod.eduvod.dto.response.BaseApiResponse;
import com.eduvod.eduvod.dto.response.superadmin.CountyResponse;
import com.eduvod.eduvod.dto.response.superadmin.SubCountyResponse;

import java.util.List;

public interface CountyService {
    BaseApiResponse<CountyResponse> createCounty(CountyRequest request);
    BaseApiResponse<CountyResponse> updateCounty(Long id, CountyRequest request);
    BaseApiResponse<String> deleteCounty(Long id);
    BaseApiResponse<CountyResponse> getByCountyId(Long id);
    BaseApiResponse<List<CountyResponse>> getAllCounties();
    BaseApiResponse<List<CountyResponse>> getByRegionId(Long regionId);
    BaseApiResponse<List<SubCountyResponse>> getSubCountiesByCountyId(Long countyId);

}
