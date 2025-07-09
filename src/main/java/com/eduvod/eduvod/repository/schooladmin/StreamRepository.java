package com.eduvod.eduvod.repository.schooladmin;

import com.eduvod.eduvod.model.schooladmin.SchoolClass;
import com.eduvod.eduvod.model.schooladmin.Stream;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StreamRepository extends JpaRepository<Stream, Long> {
    List<Stream> findBySchoolClass(SchoolClass schoolClass);
    Optional<Stream> findById(Long id);
}
