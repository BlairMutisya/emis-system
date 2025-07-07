package com.eduvod.eduvod.model.schooladmin;

import com.eduvod.eduvod.model.superadmin.Grade;
import com.eduvod.eduvod.model.superadmin.School;
import com.eduvod.eduvod.model.shared.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "classes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolClass extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String academicYear; // e.g., 2024

    @ManyToOne
    @JoinColumn(name = "grade_id")
    private Grade grade;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    private String name; // e.g., Class of 2024 Grade 2
}
