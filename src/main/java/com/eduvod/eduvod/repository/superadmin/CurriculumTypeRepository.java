package com.eduvod.eduvod.repository.superadmin;

import com.eduvod.eduvod.model.superadmin.CurriculumType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurriculumTypeRepository extends JpaRepository<CurriculumType, Long> {
    Optional<CurriculumType> findByName(String name);
}
