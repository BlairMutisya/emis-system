package com.eduvod.eduvod.repository.schooladmin;

import com.eduvod.eduvod.model.schooladmin.StreamTeacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StreamTeacherRepository extends JpaRepository<StreamTeacher, Long> {
    List<StreamTeacher> findByStreamIdAndDeletedFalse(Long streamId);
}

