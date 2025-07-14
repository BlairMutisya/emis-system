package com.eduvod.eduvod.service.superadmin;

import com.eduvod.eduvod.dto.request.superadmin.AssignSchoolRequest;
import com.eduvod.eduvod.dto.request.superadmin.SchoolAdminRequest;
import com.eduvod.eduvod.dto.request.superadmin.UpdateSchoolAdminPasswordRequest;
import com.eduvod.eduvod.dto.response.superadmin.AssignSchoolResponse;
import com.eduvod.eduvod.dto.response.superadmin.SchoolAdminResponse;
import com.eduvod.eduvod.enums.UserStatus;
import com.eduvod.eduvod.dto.response.BaseApiResponse;

import java.util.List;

public interface SchoolAdminService {
    SchoolAdminResponse createSchoolAdmin(SchoolAdminRequest request);
    List<SchoolAdminResponse> getAllSchoolAdmins();
//    void assignSchool(AssignSchoolRequest request);
    void updateStatus(Long adminId, UserStatus status);
    void softDelete(Long id);
    void updatePassword(Long id, UpdateSchoolAdminPasswordRequest request);
    BaseApiResponse<String> unassignSchool(Long schoolAdminId);
    BaseApiResponse<AssignSchoolResponse> assignSchool(AssignSchoolRequest request);

}
