package com.eduvod.eduvod.service.superadmin.impl;

import com.eduvod.eduvod.dto.response.BaseApiResponse;
import com.eduvod.eduvod.dto.response.superadmin.DashboardResponse;
import com.eduvod.eduvod.enums.Gender;
import com.eduvod.eduvod.model.schooladmin.Student;
import com.eduvod.eduvod.repository.schooladmin.*;
import com.eduvod.eduvod.service.superadmin.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final StudentRepository studentRepository;
    private final GuardianRepository guardianRepository;
    private final TeacherRepository teacherRepository;
    private final ClassRepository classRepository;
    private final StreamRepository streamRepository;

    @Override
    public BaseApiResponse<DashboardResponse> getDashboardStats() {
        Map<String, Long> studentCountByGender = new HashMap<>();
        Map<String, Long> differentlyAbledByGender = new HashMap<>();
        Map<String, Long> teacherCountByGender = new HashMap<>();
        Map<String, Long> studentsPerClass = new HashMap<>();
        Map<String, Long> studentsPerStream = new HashMap<>();

        // Student counts by gender
        for (Gender gender : Gender.values()) {
            long count = studentRepository.countByGender(gender);
            studentCountByGender.put(gender.name(), count);
        }

        // Differently abled students by gender
        for (Gender gender : Gender.values()) {
            long count = studentRepository.countByGenderAndDifferentlyAbledTrue(gender);
            differentlyAbledByGender.put(gender.name(), count);
        }

        // Teacher counts by gender
        for (Gender gender : Gender.values()) {
            long count = teacherRepository.countByGender(gender);
            teacherCountByGender.put(gender.name(), count);
        }

        // Guardian count
        long guardianCount = guardianRepository.count();

        // Students per class
        classRepository.findAll().forEach(clazz -> {
            long count = studentRepository.countByStream_Class_Id(clazz.getId());
            studentsPerClass.put(clazz.getName(), count);
        });

        // Students per stream
        streamRepository.findAll().forEach(stream -> {
            long count = studentRepository.countByStream(stream);
            studentsPerStream.put(stream.getName(), count);
        });

        DashboardResponse response = DashboardResponse.builder()
                .studentCountByGender(studentCountByGender)
                .differentlyAbledByGender(differentlyAbledByGender)
                .teacherCountByGender(teacherCountByGender)
                .guardianCount(guardianCount)
                .studentsPerClass(studentsPerClass)
                .studentsPerStream(studentsPerStream)
                .build();

        return BaseApiResponse.success("Dashboard stats fetched successfully", response);
    }
}
