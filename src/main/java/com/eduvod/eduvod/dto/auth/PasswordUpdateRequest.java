package com.eduvod.eduvod.dto.auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PasswordUpdateRequest {
    private String token;
    private String code;
    private String email;
    private String newPassword;
}
