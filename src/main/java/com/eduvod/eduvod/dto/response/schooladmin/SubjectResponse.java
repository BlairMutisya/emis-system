package com.eduvod.eduvod.dto.response.schooladmin;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubjectResponse {
    private Long id;
    private String name;
    private boolean compulsory;
}
