package com.eduvod.eduvod.dto.auth;

import com.eduvod.eduvod.model.shared.RoleType;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private RoleType role;
}