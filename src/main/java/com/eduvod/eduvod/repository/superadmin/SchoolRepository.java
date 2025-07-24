package com.eduvod.eduvod.repository.superadmin;

import com.eduvod.eduvod.model.superadmin.School;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

public interface SchoolRepository extends JpaRepository<School, Long> {
    Optional<School> findByMoeRegNo(String moeRegNo);
    Optional<School> findByName(String name);
    boolean existsByMoeRegNo(String moeRegNo);
    boolean existsByName(String name);

    Page<School> findAll(Pageable pageable);
    @Query("SELECT s.region.name, COUNT(s.id) FROM School s GROUP BY s.region.name")
    List<Object[]> countSchoolsByRegion();

}