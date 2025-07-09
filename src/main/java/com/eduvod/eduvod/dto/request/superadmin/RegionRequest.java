package com.eduvod.eduvod.dto.request.superadmin;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegionRequest {
    @NotBlank(message = "Region name is required")
    private String name;
}
