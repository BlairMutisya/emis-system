package com.eduvod.eduvod.dto.response.schooladmin;

import com.eduvod.eduvod.enums.Gender;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeacherResponse {
    private Long id;
    private String staffNumber;
    private String firstName;
    private String lastName;
    private String email;
    private Gender gender;
    private String phone;
    private boolean disabled;
}
