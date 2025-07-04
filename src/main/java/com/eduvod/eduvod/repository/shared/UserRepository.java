package com.eduvod.eduvod.repository.shared;

import com.eduvod.eduvod.model.shared.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    List<User> findAllByDeletedAtIsNull();

    boolean existsByEmail(String email);
}