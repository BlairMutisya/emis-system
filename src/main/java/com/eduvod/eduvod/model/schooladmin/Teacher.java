package com.eduvod.eduvod.model.schooladmin;

import com.eduvod.eduvod.enums.Gender;
import com.eduvod.eduvod.model.shared.BaseEntity;
import com.eduvod.eduvod.model.superadmin.School;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "teachers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String staffNumber;
    private String firstName;
    private String lastName;
    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String phone;

    private boolean disabled = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

}
