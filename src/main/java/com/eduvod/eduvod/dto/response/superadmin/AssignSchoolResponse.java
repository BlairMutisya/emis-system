package com.eduvod.eduvod.dto.response.superadmin;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssignSchoolResponse {
    private Long schoolAdminId;             // admin.getId()
    private String username;                // admin.getUser().getUsername()
    private String email;                   // admin.getUser().getEmail()
    private Long schoolId;                  // school.getId()
    private String schoolName;              // school.getName()
    private String message;
}
