package com.eduvod.eduvod.dto.request.superadmin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchoolRequest {

    private Long id;

    private String moeRegNo;
    private String kpsaRegNo;
    private String name;

    private Long curriculumId;
    private String curriculumName;

    private Long categoryId;
    private String categoryName;

    private Long typeId;
    private String typeName;

    private Long regionId;
    private String regionName;

    private Long countyId;
    private String countyName;

    private Long subCountyId;
    private String subCountyName;

    private String location;
    private String phone;
    private String email;
    private String address;
    private String website;
    private String composition;
    private String logoUrl;
}
