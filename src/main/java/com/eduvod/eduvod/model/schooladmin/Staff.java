package com.eduvod.eduvod.model.schooladmin;

import com.eduvod.eduvod.enums.Gender;
import com.eduvod.eduvod.model.superadmin.School;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "staff")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String staffNumber;

    private String firstName;

    private String middleName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String phone;

    private String staffCategory;

    private String staffDepartment;

    private LocalDate joiningDate;

    private LocalDate dateOfBirth;

    private boolean isTeacher;

    private boolean disabled;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    @Column(nullable = false)
    private boolean deleted = false;

}
