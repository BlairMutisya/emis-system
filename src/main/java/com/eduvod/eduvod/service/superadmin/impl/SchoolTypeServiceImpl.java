package com.eduvod.eduvod.service.superadmin.impl;

import com.eduvod.eduvod.dto.request.superadmin.TypeRequest;
import com.eduvod.eduvod.dto.response.superadmin.TypeResponse;
import com.eduvod.eduvod.model.superadmin.SchoolType;
import com.eduvod.eduvod.repository.superadmin.SchoolTypeRepository;
import com.eduvod.eduvod.service.superadmin.SchoolTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SchoolTypeServiceImpl implements SchoolTypeService {

    private final SchoolTypeRepository schoolTypeRepository;

    @Override
    public TypeResponse create(TypeRequest request) {
        SchoolType schoolType = new SchoolType();
        schoolType.setName(request.getName());

        schoolType = schoolTypeRepository.save(schoolType);

        return TypeResponse.builder()
                .id(schoolType.getId())
                .name(schoolType.getName())
                .build();
    }

    @Override
    public List<TypeResponse> getAll() {
        return schoolTypeRepository.findAll().stream()
                .map(type -> TypeResponse.builder()
                        .id(type.getId())
                        .name(type.getName())
                        .build())
                .collect(Collectors.toList());
    }
}

