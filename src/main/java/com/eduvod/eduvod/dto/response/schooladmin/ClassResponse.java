package com.eduvod.eduvod.dto.response.schooladmin;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClassResponse {
    private Long id;
    private String name;
    private String academicYear;
    private String gradeName;
}
