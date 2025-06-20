package com.eduvod.eduvod.repository.superadmin;

import com.eduvod.eduvod.model.superadmin.SchoolContact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchoolContactRepository extends JpaRepository<SchoolContact, Long> {
    List<SchoolContact> findByMoeRegNo(String moeRegNo);
}