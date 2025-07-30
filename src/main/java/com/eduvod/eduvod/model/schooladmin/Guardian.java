package com.eduvod.eduvod.model.schooladmin;

import com.eduvod.eduvod.enums.Gender;
import com.eduvod.eduvod.model.superadmin.School;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "guardians")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Guardian {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String relationship;
    private String email;
    private String phone;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String emergencyContact;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "student_id", nullable = true)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;
}
