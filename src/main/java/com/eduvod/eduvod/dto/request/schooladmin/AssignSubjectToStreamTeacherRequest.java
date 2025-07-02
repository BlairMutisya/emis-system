package com.eduvod.eduvod.dto.request.schooladmin;

import lombok.Data;

import java.util.List;

@Data
public class AssignSubjectToStreamTeacherRequest {
    private Long streamTeacherId;
    private List<Long> subjectIds;
}
