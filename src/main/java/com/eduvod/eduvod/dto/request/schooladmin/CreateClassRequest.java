package com.eduvod.eduvod.dto.request.schooladmin;

import lombok.Data;

@Data
public class CreateClassRequest {
    private Long gradeId;
    private String academicYear;
}
