package com.eduvod.eduvod.repository.superadmin;

import com.eduvod.eduvod.model.superadmin.SchoolCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SchoolCategoryRepository extends JpaRepository<SchoolCategory, Long> {
    Optional<SchoolCategory> findByName(String name);
}
