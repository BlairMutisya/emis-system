package com.eduvod.eduvod.dto.response.schooladmin;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StreamTeacherResponse {
    private Long id;
    private Long teacherId;
    private String teacherName;
    private Long streamId;
    private String streamName;
}
