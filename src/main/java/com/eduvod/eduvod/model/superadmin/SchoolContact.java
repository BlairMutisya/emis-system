package com.eduvod.eduvod.model.superadmin;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "school_contacts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchoolContact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String moeRegNo;
    private String name;
    private String email;
    private String phone;
    private String designation;
}