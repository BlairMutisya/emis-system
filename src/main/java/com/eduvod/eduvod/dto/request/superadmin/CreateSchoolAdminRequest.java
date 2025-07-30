package com.eduvod.eduvod.dto.request.superadmin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSchoolAdminRequest {

    @NotBlank(message = "Username is required")
    private String username;

    @Email(message = "Valid email is required")
    private String email;

//    @Size(min = 6, message = "Password must be at least 6 characters")
//    private String password;

    private UUID schoolId;
}
