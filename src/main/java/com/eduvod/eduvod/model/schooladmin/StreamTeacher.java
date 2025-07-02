package com.eduvod.eduvod.model.schooladmin;
import com.eduvod.eduvod.model.shared.BaseEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "stream_teachers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StreamTeacher extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Teacher teacher;

    @ManyToOne(optional = false)
    private Stream stream;

    private boolean deleted;


}

