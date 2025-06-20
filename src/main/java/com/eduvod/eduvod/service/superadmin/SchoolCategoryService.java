package com.eduvod.eduvod.service.superadmin;

import com.eduvod.eduvod.dto.request.superadmin.TypeRequest;
import com.eduvod.eduvod.dto.response.superadmin.TypeResponse;

import java.util.List;

public interface SchoolCategoryService {
    TypeResponse create(TypeRequest request);
    List<TypeResponse> getAll();
}
