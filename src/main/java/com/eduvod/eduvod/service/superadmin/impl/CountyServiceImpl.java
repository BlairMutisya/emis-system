package com.eduvod.eduvod.service.superadmin.impl;

import com.eduvod.eduvod.dto.request.superadmin.CountyRequest;
import com.eduvod.eduvod.dto.response.common.BaseApiResponse;
import com.eduvod.eduvod.dto.response.superadmin.CountyResponse;
import com.eduvod.eduvod.dto.response.superadmin.SubCountyResponse;
import com.eduvod.eduvod.model.superadmin.County;
import com.eduvod.eduvod.model.superadmin.Region;
import com.eduvod.eduvod.repository.superadmin.CountyRepository;
import com.eduvod.eduvod.repository.superadmin.RegionRepository;
import com.eduvod.eduvod.repository.superadmin.SubCountyRepository;
import com.eduvod.eduvod.service.superadmin.CountyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountyServiceImpl implements CountyService {

    private final CountyRepository countyRepository;
    private final RegionRepository regionRepository;
    private final SubCountyRepository subCountyRepository;

    @Override
    public BaseApiResponse<CountyResponse> createCounty(CountyRequest request) {
        Region region = regionRepository.findById(request.getRegionId())
                .orElseThrow(() -> new RuntimeException("Region not found"));

        County county = County.builder()
                .code(request.getCode())
                .name(request.getName())
                .region(region)
                .build();

        return new BaseApiResponse<>(201, "County created", mapToResponse(countyRepository.save(county)));
    }

    @Override
    public BaseApiResponse<CountyResponse> updateCounty(Long id, CountyRequest request) {
        County county = countyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("County not found"));

        Region region = regionRepository.findById(request.getRegionId())
                .orElseThrow(() -> new RuntimeException("Region not found"));

        county.setCode(request.getCode());
        county.setName(request.getName());
        county.setRegion(region);

        return new BaseApiResponse<>(200, "County updated", mapToResponse(countyRepository.save(county)));
    }

    @Override
    public BaseApiResponse<String> deleteCounty(Long id) {
        countyRepository.deleteById(id);
        return new BaseApiResponse<>(200, "County deleted", "Deleted");
    }

    @Override
    public BaseApiResponse<CountyResponse> getByCountyId(Long id) {
        County county = countyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("County not found"));
        return new BaseApiResponse<>(200, "County fetched", mapToResponse(county));
    }

    @Override
    public BaseApiResponse<List<CountyResponse>> getAllCounties() {
        List<CountyResponse> counties = countyRepository.findAll()
                .stream().map(this::mapToResponse).toList();
        return new BaseApiResponse<>(200, "All counties fetched", counties);
    }

    @Override
    public BaseApiResponse<List<CountyResponse>> getByRegionId(Long regionId) {
        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new RuntimeException("Region not found"));
        List<CountyResponse> counties = countyRepository.findByRegion(region)
                .stream().map(this::mapToResponse).toList();
        return new BaseApiResponse<>(200, "Counties for region fetched", counties);
    }

    @Override
    public BaseApiResponse<List<SubCountyResponse>> getSubCountiesByCountyId(Long countyId) {
        County county = countyRepository.findById(countyId)
                .orElseThrow(() -> new RuntimeException("County not found"));

        List<SubCountyResponse> subCounties = subCountyRepository.findByCounty(county)
                .stream()
                .map(sub -> SubCountyResponse.builder()
                        .id(sub.getId())
                        .name(sub.getName())
                        .countyId(county.getId())
                        .countyName(county.getName())
                        .regionId(county.getRegion().getId())
                        .regionName(county.getRegion().getName())
                        .build())
                .toList();

        return new BaseApiResponse<>(200, "Subcounties for county fetched", subCounties);
    }

    private CountyResponse mapToResponse(County county) {
        return CountyResponse.builder()
                .id(county.getId())
                .code(county.getCode())
                .name(county.getName())
                .regionId(county.getRegion().getId())
                .regionName(county.getRegion().getName())
                .build();
    }
}
