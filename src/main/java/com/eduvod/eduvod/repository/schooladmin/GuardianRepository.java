package com.eduvod.eduvod.repository.schooladmin;

import com.eduvod.eduvod.model.schooladmin.Guardian;
import com.eduvod.eduvod.model.superadmin.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GuardianRepository extends JpaRepository<Guardian, Long> {

    @Query("""
    SELECT COUNT(g) FROM Guardian g
    WHERE g.student.stream.schoolClass.school = :school
""")
    long countByStudentSchool(@Param("school") School school);


}