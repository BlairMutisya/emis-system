package com.eduvod.eduvod.dto.request.schooladmin;

import com.eduvod.eduvod.enums.Gender;
import lombok.Data;

@Data
public class TeacherRequest {
    private String staffNumber;
    private String firstName;
    private String lastName;
    private String email;
    private Gender gender;
    private String phone;
}
