package com.eduvod.eduvod.service.schooladmin.impl;

import com.eduvod.eduvod.constants.ErrorMessages;
import com.eduvod.eduvod.dto.request.schooladmin.StudentRequest;
import com.eduvod.eduvod.dto.response.common.BaseApiResponse;
import com.eduvod.eduvod.dto.response.schooladmin.StudentResponse;
import com.eduvod.eduvod.exception.GuardianNotFoundException;
import com.eduvod.eduvod.exception.StreamNotFoundException;
import com.eduvod.eduvod.exception.StudentNotFoundException;
import com.eduvod.eduvod.model.schooladmin.Guardian;
import com.eduvod.eduvod.model.schooladmin.Stream;
import com.eduvod.eduvod.model.schooladmin.Student;
import com.eduvod.eduvod.repository.schooladmin.GuardianRepository;
import com.eduvod.eduvod.repository.schooladmin.StreamRepository;
import com.eduvod.eduvod.repository.schooladmin.StudentRepository;
import com.eduvod.eduvod.service.schooladmin.StudentService;
import com.eduvod.eduvod.util.ExcelStudentParserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
                .orElseThrow(() -> new StreamNotFoundException(ErrorMessages.STREAM_NOT_FOUND));

        Guardian guardian = null;
        if (request.getGuardianId() != null) {
            guardian = guardianRepository.findById(request.getGuardianId())
                    .orElseThrow(() -> new GuardianNotFoundException(ErrorMessages.GUARDIAN_NOT_FOUND));
        }

        Student student = buildStudentFromRequest(request, stream, guardian);
        student = studentRepository.save(student);

        return BaseApiResponse.success(mapToResponse(student));
    }

    @Override
    public BaseApiResponse<StudentResponse> disableStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(ErrorMessages.STUDENT_NOT_FOUND));

        student.setDisabled(true);
        studentRepository.save(student);

        return BaseApiResponse.success("Student disabled successfully", mapToResponse(student));
    }
    @Override
    public BaseApiResponse<StudentResponse> updateStudent(Long studentId, StudentRequest request) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(ErrorMessages.STUDENT_NOT_FOUND));

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
                    .orElseThrow(() -> new GuardianNotFoundException(ErrorMessages.GUARDIAN_NOT_FOUND));
            student.setGuardian(guardian);
        }

        studentRepository.save(student);
        return BaseApiResponse.success("Student updated successfully", mapToResponse(student));
    }
    @Override
    public BaseApiResponse<StudentResponse> getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(ErrorMessages.STUDENT_NOT_FOUND));

        return BaseApiResponse.success(mapToResponse(student));
    }
    @Override
    public BaseApiResponse<List<StudentResponse>> getStudentsByStreamId(Long streamId) {
        Stream stream = streamRepository.findById(streamId)
                .orElseThrow(() -> new StreamNotFoundException(ErrorMessages.STREAM_NOT_FOUND));

        List<Student> students = studentRepository.findByStream(stream);

        List<StudentResponse> responses = students.stream()
                .map(this::mapToResponse)
                .toList();

        return BaseApiResponse.success("Students fetched successfully", responses);
    }
    @Override
    public BaseApiResponse<String> importStudents(Long streamId, MultipartFile file) throws Exception {
        var stream = streamRepository.findById(streamId)
                .orElseThrow(() -> new StreamNotFoundException(ErrorMessages.STREAM_NOT_FOUND));

        List<StudentRequest> studentRequests = ExcelStudentParserUtil.parse(file);
        List<Student> studentsToSave = new ArrayList<>();

        for (StudentRequest req : studentRequests) {
            Guardian guardian = null;

            if (req.getGuardianId() != null) {
                guardian = guardianRepository.findById(req.getGuardianId())
                        .orElseThrow(() -> new GuardianNotFoundException(ErrorMessages.GUARDIAN_NOT_FOUND));
            }

            Student student = buildStudentFromRequest(req, stream, guardian);
            studentsToSave.add(student);
        }



        studentRepository.saveAll(studentsToSave);
        return new BaseApiResponse<>(201, "Students imported successfully", "Imported " + studentsToSave.size() + " students");
    }
    @Override
    public BaseApiResponse<StudentResponse> assignGuardian(Long studentId, Long guardianId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(ErrorMessages.STUDENT_NOT_FOUND));

        Guardian guardian = guardianRepository.findById(guardianId)
                .orElseThrow(() -> new GuardianNotFoundException(ErrorMessages.GUARDIAN_NOT_FOUND));

        student.setGuardian(guardian);
        studentRepository.save(student);

        return BaseApiResponse.success("Guardian assigned successfully", mapToResponse(student));
    }



    private Student buildStudentFromRequest(StudentRequest request, Stream stream, Guardian guardian) {
        return Student.builder()
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
