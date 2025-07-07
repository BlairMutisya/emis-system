package com.eduvod.eduvod.model.schooladmin;

import com.eduvod.eduvod.model.shared.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "streams")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stream extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private SchoolClass schoolClass;

}
