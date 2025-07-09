package com.eduvod.eduvod.dto.response.superadmin;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubCountyResponse {
    private Long id;
    private String name;
    private Long countyId;
    private String countyName;
    private Long regionId;
    private String regionName;
}
