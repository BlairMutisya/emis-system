package com.eduvod.eduvod.dto.request.schooladmin;

import com.eduvod.eduvod.enums.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentRequest {
    private String admissionNo;
    private String nemisNo;
    private LocalDate admissionDate;

    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate dateOfBirth;

    private String email;
    private Gender gender;
    private String bloodGroup;
    private String nationality;
    private String city;
    private String addressLine1;
    private String phone;

    private boolean differentlyAbled;

    private Long guardianId;
}
