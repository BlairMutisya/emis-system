package com.eduvod.eduvod.model.schooladmin;
import com.eduvod.eduvod.model.shared.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "teacher_subjects")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StreamTeacherSubject extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private StreamTeacher streamTeacher;

    @ManyToOne(optional = false)
    private Subject subject;

    private boolean deleted;

}
