package com.eduvod.eduvod.repository.schooladmin;

import com.eduvod.eduvod.model.schooladmin.SchoolClass;
import com.eduvod.eduvod.model.superadmin.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {
    List<SchoolClass> findBySchool(School school);
}
