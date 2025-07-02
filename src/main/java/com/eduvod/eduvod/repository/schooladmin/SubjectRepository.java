package com.eduvod.eduvod.repository.schooladmin;

import com.eduvod.eduvod.model.schooladmin.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findByDeletedFalse();
}
