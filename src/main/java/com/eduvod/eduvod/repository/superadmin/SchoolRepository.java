package com.eduvod.eduvod.repository.superadmin;

import com.eduvod.eduvod.model.superadmin.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SchoolRepository extends JpaRepository<School, Long> {
    Optional<School> findByMoeRegNo(String moeRegNo);
    Optional<School> findByName(String name);
    boolean existsByMoeRegNo(String moeRegNo);
    boolean existsByName(String name);
}