package com.eduvod.eduvod.dto.request.superadmin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CountyRequest {
    @NotBlank(message = "County name is required")
    private String name;

    @NotNull(message = "Region ID is required")
    private Long regionId;
}
