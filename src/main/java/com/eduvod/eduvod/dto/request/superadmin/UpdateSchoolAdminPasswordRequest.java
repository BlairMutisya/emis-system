package com.eduvod.eduvod.dto.request.superadmin;

import lombok.Data;

@Data
public class UpdateSchoolAdminPasswordRequest {
    private Long schoolAdminId;
    private String newPassword;
}

