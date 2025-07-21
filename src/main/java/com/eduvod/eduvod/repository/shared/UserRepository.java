package com.eduvod.eduvod.repository.shared;

import com.eduvod.eduvod.model.shared.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    List<User> findAllByDeletedAtIsNull();

    boolean existsByEmail(String email);
}