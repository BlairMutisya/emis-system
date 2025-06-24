package com.eduvod.eduvod.dto.response.schooladmin;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SchoolResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String logoUrl;
    private String category;
    private String curriculumType;
    private String schoolType;
}
