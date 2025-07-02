package com.eduvod.eduvod.dto.response.schooladmin;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentResponse {
    private Long id;
    private String admissionNo;
    private String fullName;
    private String streamName;
    private String guardianName;
    private String email;
    private boolean disabled;
}
