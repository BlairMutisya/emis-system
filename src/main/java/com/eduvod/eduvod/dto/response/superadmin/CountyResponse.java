package com.eduvod.eduvod.dto.response.superadmin;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CountyResponse {
    private Long id;
    private String name;
    private Long regionId;
    private String regionName;
}
