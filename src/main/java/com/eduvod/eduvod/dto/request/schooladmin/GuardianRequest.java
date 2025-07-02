package com.eduvod.eduvod.dto.request.schooladmin;

import com.eduvod.eduvod.enums.Gender;
import lombok.Data;

@Data
public class GuardianRequest {
    private String firstName;
    private String lastName;
    private String relationship;
    private String email;
    private String phone;
    private Gender gender;
    private String emergencyContact;
}
