package com.eduvod.eduvod.repository.superadmin;

import com.eduvod.eduvod.model.superadmin.SchoolType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SchoolTypeRepository extends JpaRepository<SchoolType, Long> {
    Optional<SchoolType> findByName(String name);
}
