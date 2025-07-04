package com.eduvod.eduvod.service.schooladmin.impl;

import com.eduvod.eduvod.dto.request.schooladmin.StudentRequest;
import com.eduvod.eduvod.dto.response.BaseApiResponse;
import com.eduvod.eduvod.dto.response.schooladmin.StudentResponse;
import com.eduvod.eduvod.model.schooladmin.Guardian;
import com.eduvod.eduvod.model.schooladmin.Stream;
import com.eduvod.eduvod.model.schooladmin.Student;
import com.eduvod.eduvod.repository.schooladmin.GuardianRepository;
import com.eduvod.eduvod.repository.schooladmin.StreamRepository;
import com.eduvod.eduvod.repository.schooladmin.StudentRepository;
import com.eduvod.eduvod.service.schooladmin.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StreamRepository streamRepository;
    private final GuardianRepository guardianRepository;

    @Override
    public BaseApiResponse<StudentResponse> createStudent(Long streamId, StudentRequest request) {
        Stream stream = streamRepository.findById(streamId)
                .orElseThrow(() -> new RuntimeException("Stream not found"));

        Guardian guardian = null;
        if (request.getGuardianId() != null) {
            guardian = guardianRepository.findById(request.getGuardianId())
                    .orElseThrow(() -> new RuntimeException("Guardian not found"));
        }

        Student student = Student.builder()
                .admissionNo(request.getAdmissionNo())
                .nemisNo(request.getNemisNo())
                .admissionDate(request.getAdmissionDate())
                .firstName(request.getFirstName())
                .middleName(request.getMiddleName())
                .lastName(request.getLastName())
                .dateOfBirth(request.getDateOfBirth())
                .email(request.getEmail())
                .gender(request.getGender())
                .bloodGroup(request.getBloodGroup())
                .nationality(request.getNationality())
                .city(request.getCity())
                .addressLine1(request.getAddressLine1())
                .phone(request.getPhone())
                .differentlyAbled(request.isDifferentlyAbled())
                .disabled(false)
                .stream(stream)
                .guardian(guardian)
                .build();

        student = studentRepository.save(student);

        return BaseApiResponse.success(mapToResponse(student));
    }
    @Override
    public BaseApiResponse<StudentResponse> disableStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        student.setDisabled(true);
        studentRepository.save(student);

        return BaseApiResponse.success("Student disabled successfully", mapToResponse(student));
    }
    @Override
    public BaseApiResponse<StudentResponse> updateStudent(Long studentId, StudentRequest request) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        student.setAdmissionNo(request.getAdmissionNo());
        student.setNemisNo(request.getNemisNo());
        student.setAdmissionDate(request.getAdmissionDate());
        student.setFirstName(request.getFirstName());
        student.setMiddleName(request.getMiddleName());
        student.setLastName(request.getLastName());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setEmail(request.getEmail());
        student.setGender(request.getGender());
        student.setBloodGroup(request.getBloodGroup());
        student.setNationality(request.getNationality());
        student.setCity(request.getCity());
        student.setAddressLine1(request.getAddressLine1());
        student.setPhone(request.getPhone());
        student.setDifferentlyAbled(request.isDifferentlyAbled());

        if (request.getGuardianId() != null) {
            Guardian guardian = guardianRepository.findById(request.getGuardianId())
                    .orElseThrow(() -> new RuntimeException("Guardian not found"));
            student.setGuardian(guardian);
        }

        studentRepository.save(student);
        return BaseApiResponse.success("Student updated successfully", mapToResponse(student));
    }
    @Override
    public BaseApiResponse<StudentResponse> getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return BaseApiResponse.success(mapToResponse(student));
    }
    @Override
    public BaseApiResponse<List<StudentResponse>> getStudentsByStreamId(Long streamId) {
        Stream stream = streamRepository.findById(streamId)
                .orElseThrow(() -> new RuntimeException("Stream not found"));

        List<Student> students = studentRepository.findByStream(stream);

        List<StudentResponse> responses = students.stream()
                .map(this::mapToResponse)
                .toList();

        return BaseApiResponse.success("Students fetched successfully", responses);
    }





    private StudentResponse mapToResponse(Student student) {
        return StudentResponse.builder()
                .id(student.getId())
                .admissionNo(student.getAdmissionNo())
                .nemisNo(student.getNemisNo())
                .admissionDate(student.getAdmissionDate())
                .firstName(student.getFirstName())
                .middleName(student.getMiddleName())
                .lastName(student.getLastName())
                .dateOfBirth(student.getDateOfBirth())
                .email(student.getEmail())
                .gender(student.getGender())
                .bloodGroup(student.getBloodGroup())
                .nationality(student.getNationality())
                .city(student.getCity())
                .addressLine1(student.getAddressLine1())
                .phone(student.getPhone())
                .differentlyAbled(student.isDifferentlyAbled())
                .disabled(student.isDisabled())
                .guardianId(student.getGuardian() != null ? student.getGuardian().getId() : null)
                .build();
    }
}
