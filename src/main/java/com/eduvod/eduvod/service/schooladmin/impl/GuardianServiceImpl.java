package com.eduvod.eduvod.service.schooladmin.impl;

import com.eduvod.eduvod.constants.ErrorMessages;
import com.eduvod.eduvod.dto.request.schooladmin.GuardianRequest;
import com.eduvod.eduvod.dto.response.schooladmin.GuardianResponse;
import com.eduvod.eduvod.exception.ResourceNotFoundException;
import com.eduvod.eduvod.model.schooladmin.Guardian;
import com.eduvod.eduvod.repository.schooladmin.GuardianRepository;
import com.eduvod.eduvod.service.schooladmin.GuardianService;
import com.eduvod.eduvod.dto.response.common.BaseApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuardianServiceImpl implements GuardianService {

    private final GuardianRepository guardianRepository;

    @Override
    public BaseApiResponse<GuardianResponse> createGuardian(GuardianRequest request) {
        Guardian guardian = mapToEntity(request);
        guardianRepository.save(guardian);
        return BaseApiResponse.success(mapToResponse(guardian));
    }

    @Override
    public BaseApiResponse<GuardianResponse> updateGuardian(Long id, GuardianRequest request) {
        Guardian guardian = guardianRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.GUARDIAN_NOT_FOUND));

        updateEntity(guardian, request);
        guardianRepository.save(guardian);

        return BaseApiResponse.success(mapToResponse(guardian));
    }


    @Override
    public BaseApiResponse<Void> deleteGuardian(Long id) {
        Guardian guardian = guardianRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.GUARDIAN_NOT_FOUND));
        guardianRepository.delete(guardian);
        return BaseApiResponse.success();
    }

    @Override
    public BaseApiResponse<List<GuardianResponse>> getAllGuardians() {
        List<GuardianResponse> responses = guardianRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return BaseApiResponse.success(responses);
    }

    private Guardian mapToEntity(GuardianRequest request) {
        return Guardian.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .relationship(request.getRelationship())
                .email(request.getEmail())
                .phone(request.getPhone())
                .gender(request.getGender())
                .emergencyContact(request.getEmergencyContact())
                .build();
    }

    private void updateEntity(Guardian guardian, GuardianRequest request) {
        guardian.setFirstName(request.getFirstName());
        guardian.setLastName(request.getLastName());
        guardian.setRelationship(request.getRelationship());
        guardian.setEmail(request.getEmail());
        guardian.setPhone(request.getPhone());
        guardian.setGender(request.getGender());
        guardian.setEmergencyContact(request.getEmergencyContact());
    }

    private GuardianResponse mapToResponse(Guardian guardian) {
        return GuardianResponse.builder()
                .id(guardian.getId())
                .firstName(guardian.getFirstName())
                .lastName(guardian.getLastName())
                .relationship(guardian.getRelationship())
                .email(guardian.getEmail())
                .phone(guardian.getPhone())
                .gender(guardian.getGender())
                .emergencyContact(guardian.getEmergencyContact())
                .build();
    }
}