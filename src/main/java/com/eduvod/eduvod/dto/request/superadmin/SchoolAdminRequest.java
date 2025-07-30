package com.eduvod.eduvod.dto.request.superadmin;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolAdminRequest {
    private String username;
    private String email;
//    private String password;
    private Long schoolId;
}
