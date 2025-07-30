package com.eduvod.eduvod.service.schooladmin.impl;

import com.eduvod.eduvod.dto.request.schooladmin.SubjectRequest;
import com.eduvod.eduvod.dto.response.common.BaseApiResponse;
import com.eduvod.eduvod.dto.response.schooladmin.SubjectResponse;
import com.eduvod.eduvod.model.schooladmin.Subject;
import com.eduvod.eduvod.model.superadmin.School;
import com.eduvod.eduvod.model.superadmin.SchoolAdmin;
import com.eduvod.eduvod.repository.schooladmin.SubjectRepository;
import com.eduvod.eduvod.security.util.AuthUtil;
import com.eduvod.eduvod.service.schooladmin.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final AuthUtil authUtil;

    @Override
    public BaseApiResponse<SubjectResponse> createSubject(SubjectRequest request) {
        SchoolAdmin schoolAdmin = authUtil.getCurrentSchoolAdmin();
        School school = schoolAdmin.getSchool();

        if (subjectRepository.findByNameAndSchool(request.getName(), school).isPresent()) {
            throw new RuntimeException("Subject with name '" + request.getName() + "' already exists in this school.");
        }


        Subject subject = Subject.builder()
                .name(request.getName())
                .compulsory(request.isCompulsory())
                .deleted(false)
                .school(school)
                .build();

        subjectRepository.save(subject);

        return BaseApiResponse.success(mapToResponse(subject));
    }

    @Override
    public BaseApiResponse<SubjectResponse> updateSubject(Long id, SubjectRequest request) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        School school = authUtil.getCurrentSchoolAdmin().getSchool();

        subjectRepository.findByNameAndSchool(request.getName(), school)
                .filter(existing -> !existing.getId().equals(subject.getId()))
                .ifPresent(existing -> {
                    throw new RuntimeException("Another subject with name '" + request.getName() + "' already exists in this school.");
                });

        subject.setName(request.getName());
        subject.setCompulsory(request.isCompulsory());

        subjectRepository.save(subject);

        return BaseApiResponse.success(mapToResponse(subject));
    }


    @Override
    public BaseApiResponse<Void> deleteSubject(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        subject.setDeleted(true);
        subjectRepository.save(subject);

        return BaseApiResponse.success("Subject deleted successfully", null);
    }

    @Override
    public BaseApiResponse<List<SubjectResponse>> getAllSubjectsForSchool() {
        SchoolAdmin schoolAdmin = authUtil.getCurrentSchoolAdmin();
        School school = schoolAdmin.getSchool();

        if (school == null) {
            return BaseApiResponse.success(List.of());
        }

        List<Subject> subjects = subjectRepository.findBySchool(school);
        List<SubjectResponse> responseList = subjects.stream()
                .map(this::mapToResponse)
                .toList();

        return BaseApiResponse.success(responseList);
    }


    private SubjectResponse mapToResponse(Subject subject) {
        return SubjectResponse.builder()
                .id(subject.getId())
                .name(subject.getName())
                .compulsory(subject.isCompulsory())
                .build();
    }
}
