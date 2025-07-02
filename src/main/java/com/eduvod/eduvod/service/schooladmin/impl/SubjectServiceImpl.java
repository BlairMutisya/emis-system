package com.eduvod.eduvod.service.schooladmin.impl;

import com.eduvod.eduvod.dto.request.schooladmin.SubjectRequest;
import com.eduvod.eduvod.dto.response.BaseApiResponse;
import com.eduvod.eduvod.dto.response.schooladmin.SubjectResponse;
import com.eduvod.eduvod.model.schooladmin.Subject;
import com.eduvod.eduvod.repository.schooladmin.SubjectRepository;
import com.eduvod.eduvod.service.schooladmin.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    @Override
    public BaseApiResponse<SubjectResponse> createSubject(SubjectRequest request) {
        Subject subject = Subject.builder()
                .name(request.getName())
                .compulsory(request.isCompulsory())
                .deleted(false)
                .build();

        subjectRepository.save(subject);

        return BaseApiResponse.success(mapToResponse(subject));
    }

    @Override
    public BaseApiResponse<SubjectResponse> updateSubject(Long id, SubjectRequest request) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

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
    public BaseApiResponse<List<SubjectResponse>> getAllSubjects() {
        List<Subject> subjects = subjectRepository.findByDeletedFalse();

        List<SubjectResponse> responseList = subjects.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

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
