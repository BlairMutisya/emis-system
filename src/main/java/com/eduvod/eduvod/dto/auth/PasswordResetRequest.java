package com.eduvod.eduvod.dto.auth;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PasswordResetRequest {
    private String email;
}
