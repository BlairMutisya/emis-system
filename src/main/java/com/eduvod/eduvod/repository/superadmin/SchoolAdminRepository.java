package com.eduvod.eduvod.repository.superadmin;

import com.eduvod.eduvod.model.shared.User;
import com.eduvod.eduvod.model.superadmin.SchoolAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SchoolAdminRepository extends JpaRepository<SchoolAdmin, Long> {
//    Optional<SchoolAdmin> findByUsername(String username);
//    boolean existsByUsername(String username);
    Optional<SchoolAdmin> findByUser(User user);

    Optional<SchoolAdmin> findByUserId(Long id);
    @Query("SELECT sa FROM SchoolAdmin sa JOIN FETCH sa.user WHERE sa.id = :id")
    Optional<SchoolAdmin> findByIdWithUser(@Param("id") Long id);

}
