package com.eduvod.eduvod.repository.schooladmin;

import com.eduvod.eduvod.enums.Gender;
import com.eduvod.eduvod.model.schooladmin.Staff;
import com.eduvod.eduvod.model.superadmin.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    long countByGenderAndIsTeacherTrue(Gender gender);
    List<Staff> findBySchool(School school);
    List<Staff> findBySchoolAndIsTeacherTrue(School school);

    List<Staff> findBySchoolAndDeletedFalse(School school);

    Optional<Staff> findByIdAndDeletedFalse(Long id);

}
