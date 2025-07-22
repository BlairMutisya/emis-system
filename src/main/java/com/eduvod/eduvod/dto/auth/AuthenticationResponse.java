package com.eduvod.eduvod.dto.auth;

import com.eduvod.eduvod.model.shared.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    private String token;
    private Long userId;
    private String username;
    private String email;
    private RoleType role;
    private boolean mustChangePassword;
}
