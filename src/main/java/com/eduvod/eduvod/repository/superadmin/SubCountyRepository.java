package com.eduvod.eduvod.repository.superadmin;

import com.eduvod.eduvod.model.superadmin.County;
import com.eduvod.eduvod.model.superadmin.SubCounty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubCountyRepository extends JpaRepository<SubCounty, Long> {
    List<SubCounty> findByCounty(County county);
    Optional<SubCounty> findByName(String name);
}
