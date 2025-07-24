package com.eduvod.eduvod.dto.response.superadmin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {
    private Map<String, Long> studentCountByGender;
    private Map<String, Long> differentlyAbledByGender;
    private Map<String, Long> teacherCountByGender;
    private Long guardianCount;
    private Map<String, Long> studentsPerClass;
    private Map<String, Long> studentsPerStream;
    private Map<String, Long> schoolsPerRegion;
}
