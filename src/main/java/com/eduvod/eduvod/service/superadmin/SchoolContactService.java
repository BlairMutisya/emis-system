package com.eduvod.eduvod.service.superadmin;
import com.eduvod.eduvod.dto.request.superadmin.SchoolContactRequest;
import com.eduvod.eduvod.dto.response.superadmin.SchoolContactResponse;

import java.util.List;

public interface SchoolContactService {
    SchoolContactResponse create(SchoolContactRequest request);
    List<SchoolContactResponse> getByMoeRegNo(String moeRegNo);
}