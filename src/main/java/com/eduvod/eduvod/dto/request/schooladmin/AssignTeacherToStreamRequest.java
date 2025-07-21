package com.eduvod.eduvod.dto.request.schooladmin;

import lombok.Data;

@Data
public class AssignTeacherToStreamRequest {
    private Long streamId;
    private Long staffId;
}

