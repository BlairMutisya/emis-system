package com.eduvod.eduvod.dto.response.superadmin;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TypeResponse {
    private Long id;
    private String name;
}
