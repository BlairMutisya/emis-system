package com.eduvod.eduvod.dto.request.superadmin;

import lombok.Data;

@Data
public class SchoolRequest {
    private Long id;
    private String moeRegNo;
    private String kpsaRegNo;
    private String name;
    private Long curriculumId;
    private Long categoryId;
    private Long typeId;
    private String composition;
    private String phone;
    private String email;
    private Long regionId;
    private Long countyId;
    private Long subCountyId;
    private String location;
    private String address;
    private String website;
}
