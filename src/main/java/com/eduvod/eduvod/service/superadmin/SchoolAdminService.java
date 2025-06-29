package com.eduvod.eduvod.service.superadmin;

import com.eduvod.eduvod.dto.request.superadmin.AssignSchoolRequest;
import com.eduvod.eduvod.dto.request.superadmin.SchoolAdminRequest;
import com.eduvod.eduvod.dto.request.superadmin.UpdateSchoolAdminPasswordRequest;
import com.eduvod.eduvod.dto.response.superadmin.SchoolAdminResponse;
import com.eduvod.eduvod.enums.UserStatus;

import java.util.List;

public interface SchoolAdminService {
    SchoolAdminResponse createSchoolAdmin(SchoolAdminRequest request);
    List<SchoolAdminResponse> getAllSchoolAdmins();
    void assignSchool(AssignSchoolRequest request);
    void updateStatus(Long adminId, UserStatus status);
    void softDelete(Long id);
    void updatePassword(Long id, UpdateSchoolAdminPasswordRequest request);
}

