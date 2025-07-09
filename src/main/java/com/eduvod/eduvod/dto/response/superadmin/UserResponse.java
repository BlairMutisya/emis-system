package com.eduvod.eduvod.dto.response.superadmin;

import com.eduvod.eduvod.enums.UserStatus;
import com.eduvod.eduvod.model.shared.RoleType;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private RoleType role;
    private String schoolName;
    private UserStatus status;
}
