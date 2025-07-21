package com.eduvod.eduvod.service.superadmin.impl;

import com.eduvod.eduvod.dto.request.superadmin.RegionRequest;
import com.eduvod.eduvod.dto.response.common.BaseApiResponse;
import com.eduvod.eduvod.dto.response.superadmin.CountyResponse;
import com.eduvod.eduvod.dto.response.superadmin.RegionResponse;
import com.eduvod.eduvod.model.superadmin.Region;
import com.eduvod.eduvod.repository.superadmin.RegionRepository;
import com.eduvod.eduvod.service.superadmin.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    @Override
    public BaseApiResponse<RegionResponse> createRegion(RegionRequest request) {
        Region region = Region.builder().name(request.getName()).build();
        Region saved = regionRepository.save(region);
        return new BaseApiResponse<>(201, "Region created successfully", mapToResponse(saved));
    }

    @Override
    public BaseApiResponse<RegionResponse> updateRegion(Long id, RegionRequest request) {
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Region not found"));
        region.setName(request.getName());
        Region updated = regionRepository.save(region);
        return new BaseApiResponse<>(200, "Region updated", mapToResponse(updated));
    }

    @Override
    public BaseApiResponse<String> deleteRegion(Long id) {
        regionRepository.deleteById(id);
        return new BaseApiResponse<>(200, "Region deleted successfully", "Deleted");
    }

    @Override
    public BaseApiResponse<RegionResponse> getRegionById(Long id) {
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Region not found"));
        return new BaseApiResponse<>(200, "Region fetched", mapToResponse(region));
    }

    @Override
    public BaseApiResponse<List<RegionResponse>> getAllRegions() {
        List<RegionResponse> regions = regionRepository.findAll()
                .stream().map(this::mapToResponse).toList();
        return new BaseApiResponse<>(200, "All regions fetched", regions);
    }
    @Override
    public BaseApiResponse<List<CountyResponse>> getCountiesByRegionId(Long regionId) {
        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new RuntimeException("Region not found"));

        List<CountyResponse> counties = region.getCounties()  // ✅ Correct method here
                .stream()
                .map(county -> CountyResponse.builder()
                        .id(county.getId())
                        .name(county.getName())
                        .build())
                .toList();

        return new BaseApiResponse<>(200, "Counties fetched", counties);
    }



    private RegionResponse mapToResponse(Region region) {
        return RegionResponse.builder()
                .id(region.getId())
                .name(region.getName())
                .build();
    }
}
