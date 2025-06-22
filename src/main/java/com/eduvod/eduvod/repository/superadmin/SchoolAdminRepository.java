package com.eduvod.eduvod.repository.superadmin;

import com.eduvod.eduvod.model.superadmin.SchoolAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SchoolAdminRepository extends JpaRepository<SchoolAdmin, Long> {
    Optional<SchoolAdmin> findByUsername(String username);
    boolean existsByUsername(String username);
}
