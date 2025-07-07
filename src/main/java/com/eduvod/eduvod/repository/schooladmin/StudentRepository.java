package com.eduvod.eduvod.repository.schooladmin;

import com.eduvod.eduvod.enums.Gender;
import com.eduvod.eduvod.model.schooladmin.Stream;
import com.eduvod.eduvod.model.schooladmin.Student;
import com.eduvod.eduvod.model.superadmin.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByStream(Stream stream);
    long countByGender(Gender gender);
    long countByGenderAndDifferentlyAbledTrue(Gender gender);
    long countByStream_SchoolClass_Id(Long classId);
    long countByStream(Stream stream);
    List<Student> findByStream_SchoolClass_School(School school);


}
