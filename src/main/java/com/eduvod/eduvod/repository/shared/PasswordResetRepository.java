package com.eduvod.eduvod.repository.shared;

import com.eduvod.eduvod.model.shared.PasswordReset;
import com.eduvod.eduvod.model.shared.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, Long> {

    Optional<PasswordReset> findByToken(String token); // Web

    Optional<PasswordReset> findByEmailAndCode(String email, String code); // Mobile

    @Modifying
    @Transactional
    @Query("DELETE FROM PasswordReset p WHERE p.user = :user")
    void deleteByUser(@Param("user") User user);
}
