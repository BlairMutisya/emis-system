package com.eduvod.eduvod.repository.superadmin;

import com.eduvod.eduvod.model.superadmin.County;
import com.eduvod.eduvod.model.superadmin.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Long> {
    Optional<Region> findByName(String name); // ✅

}
