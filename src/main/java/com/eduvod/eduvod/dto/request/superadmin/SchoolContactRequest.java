package com.eduvod.eduvod.dto.request.superadmin;

import lombok.Data;

@Data
public class SchoolContactRequest {
    private String moeRegNo;
    private String name;
    private String email;
    private String phone;
    private String designation;
}