package com.eduvod.eduvod.dto.response.superadmin;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SchoolContactResponse {
    private Long id;
    private String moeRegNo;
    private String name;
    private String email;
    private String phone;
    private String designation;
}