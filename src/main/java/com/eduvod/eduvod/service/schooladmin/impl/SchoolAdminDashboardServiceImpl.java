package com.eduvod.eduvod.service.schooladmin.impl;

import com.eduvod.eduvod.dto.response.BaseApiResponse;
import com.eduvod.eduvod.dto.response.schooladmin.SchoolAdminDashboardResponse;
import com.eduvod.eduvod.enums.Gender;
import com.eduvod.eduvod.model.schooladmin.Student;
import com.eduvod.eduvod.model.schooladmin.Teacher;
import com.eduvod.eduvod.repository.schooladmin.*;
import com.eduvod.eduvod.security.util.AuthUtil;
import com.eduvod.eduvod.service.schooladmin.SchoolAdminDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SchoolAdminDashboardServiceImpl implements SchoolAdminDashboardService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final GuardianRepository guardianRepository;
    private final StreamRepository streamRepository;
    private final ClassRepository classRepository;
    private final AuthUtil authUtil;

    @Override
    public BaseApiResponse<SchoolAdminDashboardResponse> getDashboardStats() {
        var school = authUtil.getCurrentSchoolAdmin().getSchool();

        List<Student> students = studentRepository.findByStream_Class_School(school);
        List<Teacher> teachers = teacherRepository.findBySchool(school);

        // Students by gender
        Map<String, Long> studentCountByGender = students.stream()
                .collect(Collectors.groupingBy(s -> s.getGender() != null ? s.getGender().name() : "UNSPECIFIED", Collectors.counting()));

        // Differently abled students by gender
        Map<String, Long> differentlyAbledCountByGender = students.stream()
                .filter(Student::isDifferentlyAbled)
                .collect(Collectors.groupingBy(s -> s.getGender() != null ? s.getGender().name() : "UNSPECIFIED", Collectors.counting()));

        // Teachers by gender
        Map<String, Long> teacherCountByGender = teachers.stream()
                .collect(Collectors.groupingBy(t -> t.getGender() != null ? t.getGender().name() : "UNSPECIFIED", Collectors.counting()));

        // Guardians count
        long guardianCount = guardianRepository.countBySchool(school);

        // Students per class
        Map<String, Long> studentsPerClass = students.stream()
                .filter(s -> s.getStream() != null && s.getStream().getClazz() != null)
                .collect(Collectors.groupingBy(
                        s -> s.getStream().getClazz().getName(),
                        Collectors.counting()
                ));

        // Students per stream
        Map<String, Long> studentsPerStream = students.stream()
                .filter(s -> s.getStream() != null)
                .collect(Collectors.groupingBy(
                        s -> s.getStream().getName(),
                        Collectors.counting()
                ));

        var response = SchoolAdminDashboardResponse.builder()
                .studentCountByGender(studentCountByGender)
                .differentlyAbledStudentCountByGender(differentlyAbledCountByGender)
                .teacherCountByGender(teacherCountByGender)
                .totalGuardians(guardianCount)
                .studentCountPerClass(studentsPerClass)
                .studentCountPerStream(studentsPerStream)
                .build();

        return BaseApiResponse.success(response);
    }
}
