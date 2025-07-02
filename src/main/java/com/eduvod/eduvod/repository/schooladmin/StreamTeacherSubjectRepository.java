package com.eduvod.eduvod.repository.schooladmin;

import com.eduvod.eduvod.model.schooladmin.StreamTeacherSubject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StreamTeacherSubjectRepository extends JpaRepository<StreamTeacherSubject, Long> {
    List<StreamTeacherSubject> findByStreamTeacherIdAndDeletedFalse(Long streamTeacherId);
}

