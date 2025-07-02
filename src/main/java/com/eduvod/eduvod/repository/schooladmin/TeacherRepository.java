package com.eduvod.eduvod.repository.schooladmin;

import com.eduvod.eduvod.enums.Gender;
import com.eduvod.eduvod.model.schooladmin.Teacher;
import com.eduvod.eduvod.model.superadmin.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    long countByGender(Gender gender);
    List<Teacher> findBySchool(School school);


}
