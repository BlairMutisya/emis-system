package com.eduvod.eduvod.repository.shared;

import com.eduvod.eduvod.model.shared.PasswordReset;
import com.eduvod.eduvod.model.shared.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, Long> {
    Optional<PasswordReset> findByToken(String token);
    void deleteByUser(User user);
}
