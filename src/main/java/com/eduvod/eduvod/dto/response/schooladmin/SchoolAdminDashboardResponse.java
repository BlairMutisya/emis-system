package com.eduvod.eduvod.dto.response.schooladmin;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class SchoolAdminDashboardResponse {
    private Map<String, Long> studentCountByGender;
    private Map<String, Long> differentlyAbledStudentCountByGender;
    private Map<String, Long> teacherCountByGender;
    private long totalGuardians;
    private Map<String, Long> studentCountPerClass;
    private Map<String, Long> studentCountPerStream;
}
