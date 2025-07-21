package com.eduvod.eduvod.dto.response.schooladmin;

import com.eduvod.eduvod.enums.Gender;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class StaffResponse {
    private Long id;
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
    private boolean disabled;
}
