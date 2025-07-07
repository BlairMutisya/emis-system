package com.eduvod.eduvod.service.schooladmin;

import com.eduvod.eduvod.dto.request.schooladmin.CreateClassRequest;
import com.eduvod.eduvod.dto.response.BaseApiResponse;
import com.eduvod.eduvod.dto.response.schooladmin.ClassResponse;

import java.util.List;

public interface SchoolClassService {
    BaseApiResponse<ClassResponse> createClass(CreateClassRequest request);
    BaseApiResponse<List<ClassResponse>> getAllClassesForSchool();
}
