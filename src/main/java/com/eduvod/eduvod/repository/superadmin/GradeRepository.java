package com.eduvod.eduvod.repository.superadmin;

import com.eduvod.eduvod.model.superadmin.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    boolean existsByName(String name);
}
