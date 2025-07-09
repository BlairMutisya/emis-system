package com.eduvod.eduvod.dto.request.superadmin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubCountyRequest {
    @NotBlank(message = "SubCounty name is required")
    private String name;

    @NotNull(message = "County ID is required")
    private Long countyId;
}
