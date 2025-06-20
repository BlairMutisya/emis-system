package com.eduvod.eduvod.service.superadmin.impl;

import com.eduvod.eduvod.dto.request.superadmin.TypeRequest;
import com.eduvod.eduvod.dto.response.superadmin.TypeResponse;
import com.eduvod.eduvod.model.superadmin.CurriculumType;
import com.eduvod.eduvod.repository.superadmin.CurriculumTypeRepository;
import com.eduvod.eduvod.service.superadmin.CurriculumTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurriculumTypeServiceImpl implements CurriculumTypeService {

    private final CurriculumTypeRepository curriculumTypeRepository;

    @Override
    public TypeResponse create(TypeRequest request) {
        CurriculumType curriculumType = new CurriculumType();
        curriculumType.setName(request.getName());

        curriculumType = curriculumTypeRepository.save(curriculumType);

        return TypeResponse.builder()
                .id(curriculumType.getId())
                .name(curriculumType.getName())
                .build();
    }

    @Override
    public List<TypeResponse> getAll() {
        return curriculumTypeRepository.findAll().stream()
                .map(c -> TypeResponse.builder()
                        .id(c.getId())
                        .name(c.getName())
                        .build())
                .collect(Collectors.toList());
    }
}
