package com.eduvod.eduvod.dto.response.superadmin;

import com.eduvod.eduvod.enums.UserStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SchoolAdminResponse {
    private Long id;
    private String username;
    private String email;
    private String schoolName;
    private UserStatus status;
}
