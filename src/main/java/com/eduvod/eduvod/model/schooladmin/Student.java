package com.eduvod.eduvod.model.schooladmin;
import com.eduvod.eduvod.enums.Gender;
import com.eduvod.eduvod.model.shared.BaseEntity;
import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String admissionNo;

    private String nemisNo;

    private LocalDate admissionDate;

    @Column(nullable = false)
    private String firstName;

    private String middleName;

    @Column(nullable = false)
    private String lastName;

    private LocalDate dateOfBirth;

    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String bloodGroup;
    private String nationality;
    private String city;
    private String addressLine1;
    private String phone;

    private boolean differentlyAbled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stream_id")
    private Stream stream;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "guardian_id")
    private Guardian guardian;

    private boolean disabled;
}
