package com.eduvod.eduvod.dto.response.schooladmin;

import com.eduvod.eduvod.enums.Gender;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GuardianResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String relationship;
    private String email;
    private String phone;
    private Gender gender;
    private String emergencyContact;
}