package com.eduvod.eduvod.model.schooladmin;

import com.eduvod.eduvod.model.shared.BaseEntity;
import com.eduvod.eduvod.model.superadmin.School;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "subjects",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name", "school_id"})
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subject extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private boolean compulsory;

    private boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

}
