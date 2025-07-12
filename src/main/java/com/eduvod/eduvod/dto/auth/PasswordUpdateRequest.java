package com.eduvod.eduvod.dto.auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordUpdateRequest {
    private String token;
    private String newPassword;
}
