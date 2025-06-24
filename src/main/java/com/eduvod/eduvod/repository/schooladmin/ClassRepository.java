package com.eduvod.eduvod.repository.schooladmin;

import com.eduvod.eduvod.model.schooladmin.Class;
import com.eduvod.eduvod.model.superadmin.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassRepository extends JpaRepository<Class, Long> {
    List<Class> findBySchool(School school);
}
