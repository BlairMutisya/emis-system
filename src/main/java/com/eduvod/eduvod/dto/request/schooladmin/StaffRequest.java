package com.eduvod.eduvod.dto.request.schooladmin;

import com.eduvod.eduvod.enums.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StaffRequest {
    private String staffNumber;
    private String firstName;
    private String middleName;
    private String lastName;
    private Gender gender;
    private String phone;
    private String staffCategory;
    private String staffDepartment;
    private LocalDate joiningDate;
    private LocalDate dateOfBirth;
    private boolean isTeacher;
}
