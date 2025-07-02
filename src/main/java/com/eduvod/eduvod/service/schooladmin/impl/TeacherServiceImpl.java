package com.eduvod.eduvod.service.schooladmin.impl;

import com.eduvod.eduvod.dto.request.schooladmin.TeacherRequest;
import com.eduvod.eduvod.dto.response.BaseApiResponse;
import com.eduvod.eduvod.dto.response.schooladmin.TeacherResponse;
import com.eduvod.eduvod.model.schooladmin.Teacher;
import com.eduvod.eduvod.repository.schooladmin.TeacherRepository;
import com.eduvod.eduvod.service.schooladmin.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    @Override
    public BaseApiResponse<TeacherResponse> createTeacher(TeacherRequest request) {
        Teacher teacher = Teacher.builder()
                .staffNumber(request.getStaffNumber())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .gender(request.getGender())
                .phone(request.getPhone())
                .disabled(false)
                .build();

        teacher = teacherRepository.save(teacher);
        return BaseApiResponse.success(mapToResponse(teacher));
    }

    @Override
    public BaseApiResponse<TeacherResponse> updateTeacher(Long id, TeacherRequest request) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        teacher.setStaffNumber(request.getStaffNumber());
        teacher.setFirstName(request.getFirstName());
        teacher.setLastName(request.getLastName());
        teacher.setEmail(request.getEmail());
        teacher.setGender(request.getGender());
        teacher.setPhone(request.getPhone());

        teacher = teacherRepository.save(teacher);
        return BaseApiResponse.success(mapToResponse(teacher));
    }

    @Override
    public BaseApiResponse<Void> disableTeacher(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        teacher.setDisabled(true);
        teacherRepository.save(teacher);
        return BaseApiResponse.success("Teacher disabled", null);
    }

    @Override
    public BaseApiResponse<List<TeacherResponse>> getAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();

        return BaseApiResponse.success(
                teachers.stream().map(this::mapToResponse).collect(Collectors.toList())
        );
    }

    private TeacherResponse mapToResponse(Teacher teacher) {
        return TeacherResponse.builder()
                .id(teacher.getId())
                .staffNumber(teacher.getStaffNumber())
                .firstName(teacher.getFirstName())
                .lastName(teacher.getLastName())
                .email(teacher.getEmail())
                .gender(teacher.getGender())
                .phone(teacher.getPhone())
                .disabled(teacher.isDisabled())
                .build();
    }
}
