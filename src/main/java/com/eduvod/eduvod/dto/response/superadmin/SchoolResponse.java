package com.eduvod.eduvod.dto.response.superadmin;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SchoolResponse {
    private Long id;
    private String moeRegNo;
    private String name;
    private String email;
    private String mobile;
    private String curriculumType;
    private String category;
    private String type;
}
