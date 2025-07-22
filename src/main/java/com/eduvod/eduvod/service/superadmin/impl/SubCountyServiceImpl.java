package com.eduvod.eduvod.service.superadmin.impl;

import com.eduvod.eduvod.constants.ErrorMessages;
import com.eduvod.eduvod.dto.request.superadmin.SubCountyRequest;
import com.eduvod.eduvod.dto.response.common.BaseApiResponse;
import com.eduvod.eduvod.dto.response.superadmin.SubCountyResponse;
import com.eduvod.eduvod.exception.ResourceNotFoundException;
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
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.COUNTY_NOT_FOUND));

        SubCounty subCounty = SubCounty.builder()
                .name(request.getName())
                .county(county)
                .build();

        SubCounty saved = subCountyRepository.save(subCounty);

        return new BaseApiResponse<>(
                ErrorMessages.STATUS_CREATED,
                ErrorMessages.SUB_COUNTY_CREATED,
                mapToResponse(saved)
        );
    }

    @Override
    public BaseApiResponse<SubCountyResponse> updateSubCounty(Long id, SubCountyRequest request) {
        SubCounty subCounty = subCountyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.SUB_COUNTY_NOT_FOUND));

        County county = countyRepository.findById(request.getCountyId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.COUNTY_NOT_FOUND));

        subCounty.setName(request.getName());
        subCounty.setCounty(county);

        SubCounty updated = subCountyRepository.save(subCounty);

        return new BaseApiResponse<>(
                ErrorMessages.STATUS_OK,
                ErrorMessages.SUB_COUNTY_UPDATED,
                mapToResponse(updated)
        );
    }

    @Override
    public BaseApiResponse<String> deleteSubCounty(Long id) {
        if (!subCountyRepository.existsById(id)) {
            throw new ResourceNotFoundException(ErrorMessages.SUB_COUNTY_NOT_FOUND);
        }

        subCountyRepository.deleteById(id);
        return new BaseApiResponse<>(ErrorMessages.STATUS_OK, ErrorMessages.SUB_COUNTY_DELETED, "Deleted");
    }

    @Override
    public BaseApiResponse<SubCountyResponse> getBySubCountyId(Long id) {
        SubCounty subCounty = subCountyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.SUB_COUNTY_NOT_FOUND));

        return new BaseApiResponse<>(
                ErrorMessages.STATUS_OK,
                ErrorMessages.SUB_COUNTY_FETCHED,
                mapToResponse(subCounty)
        );
    }

    @Override
    public BaseApiResponse<List<SubCountyResponse>> getAllSubCounties() {
        List<SubCountyResponse> subs = subCountyRepository.findAll()
                .stream().map(this::mapToResponse).toList();

        return new BaseApiResponse<>(
                ErrorMessages.STATUS_OK,
                ErrorMessages.ALL_SUB_COUNTIES_FETCHED,
                subs
        );
    }

    @Override
    public BaseApiResponse<List<SubCountyResponse>> getByCountyId(Long countyId) {
        County county = countyRepository.findById(countyId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.COUNTY_NOT_FOUND));

        List<SubCountyResponse> subs = subCountyRepository.findByCounty(county)
                .stream().map(this::mapToResponse).toList();

        return new BaseApiResponse<>(
                ErrorMessages.STATUS_OK,
                ErrorMessages.SUB_COUNTIES_FOR_COUNTY_FETCHED,
                subs
        );
    }

    private SubCountyResponse mapToResponse(SubCounty subCounty) {
        return SubCountyResponse.builder()
                .id(subCounty.getId())
                .name(subCounty.getName())
                .countyId(subCounty.getCounty().getId())
                .build();
    }
}
