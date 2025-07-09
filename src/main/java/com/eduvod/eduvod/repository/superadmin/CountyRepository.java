package com.eduvod.eduvod.repository.superadmin;

import com.eduvod.eduvod.model.superadmin.County;
import com.eduvod.eduvod.model.superadmin.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CountyRepository extends JpaRepository<County, Long> {
    List<County> findByRegion(Region region);
    Optional<County> findByName(String name);
}
