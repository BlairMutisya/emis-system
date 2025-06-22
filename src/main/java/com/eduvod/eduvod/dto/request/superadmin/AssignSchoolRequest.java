package com.eduvod.eduvod.dto.request.superadmin;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignSchoolRequest {

    @NotNull(message = "School Admin ID is required")
    private Long schoolAdminId;

    @NotNull(message = "School ID is required")
    private Long schoolId;
}
