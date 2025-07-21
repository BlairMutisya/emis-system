package com.eduvod.eduvod.dto.response.schooladmin;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StreamTeacherResponse {
    private Long id;
    private Long staffId;
    private String staffName;
    private Long streamId;
    private String streamName;
}
