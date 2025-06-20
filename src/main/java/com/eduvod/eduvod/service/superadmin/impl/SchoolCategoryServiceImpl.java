package com.eduvod.eduvod.service.superadmin.impl;

import com.eduvod.eduvod.dto.request.superadmin.TypeRequest;
import com.eduvod.eduvod.dto.response.superadmin.TypeResponse;
import com.eduvod.eduvod.model.superadmin.SchoolCategory;
import com.eduvod.eduvod.repository.superadmin.SchoolCategoryRepository;
import com.eduvod.eduvod.service.superadmin.SchoolCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SchoolCategoryServiceImpl implements SchoolCategoryService {

    private final SchoolCategoryRepository schoolCategoryRepository;

    @Override
    public TypeResponse create(TypeRequest request) {
        SchoolCategory schoolCategory = new SchoolCategory();
        schoolCategory.setName(request.getName());

        schoolCategory = schoolCategoryRepository.save(schoolCategory);

        return TypeResponse.builder()
                .id(schoolCategory.getId())
                .name(schoolCategory.getName())
                .build();
    }

    @Override
    public List<TypeResponse> getAll() {
        return schoolCategoryRepository.findAll().stream()
                .map(category -> TypeResponse.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .build())
                .collect(Collectors.toList());
    }
}

