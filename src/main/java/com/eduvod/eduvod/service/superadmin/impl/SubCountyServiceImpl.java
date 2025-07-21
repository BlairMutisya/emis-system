package com.eduvod.eduvod.service.superadmin.impl;

import com.eduvod.eduvod.dto.request.superadmin.SubCountyRequest;
import com.eduvod.eduvod.dto.response.common.BaseApiResponse;
import com.eduvod.eduvod.dto.response.superadmin.SubCountyResponse;
import com.eduvod.eduvod.model.superadmin.County;
import com.eduvod.eduvod.model.superadmin.SubCounty;
import com.eduvod.eduvod.repository.superadmin.CountyRepository;
import com.eduvod.eduvod.repository.superadmin.SubCountyRepository;
import com.eduvod.eduvod.service.superadmin.SubCountyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class SubCountyServiceImpl implements SubCountyService {

    private final SubCountyRepository subCountyRepository;
    private final CountyRepository countyRepository;

    @Override
    public BaseApiResponse<SubCountyResponse> createSubCounty(SubCountyRequest request) {
        County county = countyRepository.findById(request.getCountyId())
                .orElseThrow(() -> new RuntimeException("County not found"));
        SubCounty subCounty = SubCounty.builder()
                .name(request.getName())
                .county(county)
                .build();
        return new BaseApiResponse<>(201, "SubCounty created", mapToResponse(subCountyRepository.save(subCounty)));
    }

    @Override
    public BaseApiResponse<SubCountyResponse> updateSubCounty(Long id, SubCountyRequest request) {
        SubCounty subCounty = subCountyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SubCounty not found"));
        County county = countyRepository.findById(request.getCountyId())
                .orElseThrow(() -> new RuntimeException("County not found"));
        subCounty.setName(request.getName());
        subCounty.setCounty(county);
        return new BaseApiResponse<>(200, "SubCounty updated", mapToResponse(subCountyRepository.save(subCounty)));
    }

    @Override
    public BaseApiResponse<String> deleteSubCounty(Long id) {
        subCountyRepository.deleteById(id);
        return new BaseApiResponse<>(200, "SubCounty deleted", "Deleted");
    }

    @Override
    public BaseApiResponse<SubCountyResponse> getBySubCountyId(Long id) {
        SubCounty subCounty = subCountyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SubCounty not found"));
        return new BaseApiResponse<>(200, "SubCounty fetched", mapToResponse(subCounty));
    }

    @Override
    public BaseApiResponse<List<SubCountyResponse>> getAllSubCounties() {
        List<SubCountyResponse> subs = subCountyRepository.findAll()
                .stream().map(this::mapToResponse).toList();
        return new BaseApiResponse<>(200, "All subcounties fetched", subs);
    }

    @Override
    public BaseApiResponse<List<SubCountyResponse>> getByCountyId(Long countyId) {
        County county = countyRepository.findById(countyId)
                .orElseThrow(() -> new RuntimeException("County not found"));
        List<SubCountyResponse> subs = subCountyRepository.findByCounty(county)
                .stream().map(this::mapToResponse).toList();
        return new BaseApiResponse<>(200, "Subcounties for county fetched", subs);
    }

    private SubCountyResponse mapToResponse(SubCounty subCounty) {
        return SubCountyResponse.builder()
                .id(subCounty.getId())
                .name(subCounty.getName())
                .countyId(subCounty.getCounty().getId())
                .build();
    }
}
