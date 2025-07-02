package com.eduvod.eduvod.service.schooladmin.impl;

import com.eduvod.eduvod.dto.request.schooladmin.AssignSubjectToStreamTeacherRequest;
import com.eduvod.eduvod.dto.request.schooladmin.AssignTeacherToStreamRequest;
import com.eduvod.eduvod.dto.response.BaseApiResponse;
import com.eduvod.eduvod.dto.response.schooladmin.StreamTeacherResponse;
import com.eduvod.eduvod.model.schooladmin.*;
import com.eduvod.eduvod.repository.schooladmin.*;
import com.eduvod.eduvod.service.schooladmin.StreamTeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StreamTeacherServiceImpl implements StreamTeacherService {

    private final StreamRepository streamRepository;
    private final TeacherRepository teacherRepository;
    private final StreamTeacherRepository streamTeacherRepository;
    private final SubjectRepository subjectRepository;
    private final StreamTeacherSubjectRepository streamTeacherSubjectRepository;

    @Override
    public BaseApiResponse<StreamTeacherResponse> assignTeacherToStream(AssignTeacherToStreamRequest request) {
        Stream stream = streamRepository.findById(request.getStreamId())
                .orElseThrow(() -> new RuntimeException("Stream not found"));
        Teacher teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        StreamTeacher streamTeacher = StreamTeacher.builder()
                .stream(stream)
                .teacher(teacher)
                .deleted(false)
                .build();

        streamTeacher = streamTeacherRepository.save(streamTeacher);

        return BaseApiResponse.success(mapToResponse(streamTeacher));
    }

    @Override
    public BaseApiResponse<String> assignSubjectsToStreamTeacher(AssignSubjectToStreamTeacherRequest request) {
        StreamTeacher streamTeacher = streamTeacherRepository.findById(request.getStreamTeacherId())
                .orElseThrow(() -> new RuntimeException("StreamTeacher not found"));

        for (Long subjectId : request.getSubjectIds()) {
            Subject subject = subjectRepository.findById(subjectId)
                    .orElseThrow(() -> new RuntimeException("Subject not found"));

            StreamTeacherSubject sts = StreamTeacherSubject.builder()
                    .streamTeacher(streamTeacher)
                    .subject(subject)
                    .deleted(false)
                    .build();

            streamTeacherSubjectRepository.save(sts);
        }

        return BaseApiResponse.success("Subjects assigned successfully");
    }

    @Override
    public BaseApiResponse<List<StreamTeacherResponse>> getTeachersByStream(Long streamId) {
        List<StreamTeacher> streamTeachers = streamTeacherRepository.findByStreamIdAndDeletedFalse(streamId);

        List<StreamTeacherResponse> responses = streamTeachers.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return BaseApiResponse.success(responses);
    }

    private StreamTeacherResponse mapToResponse(StreamTeacher entity) {
        return StreamTeacherResponse.builder()
                .id(entity.getId())
                .teacherId(entity.getTeacher().getId())
                .teacherName(entity.getTeacher().getFirstName() + " " + entity.getTeacher().getLastName())
                .streamId(entity.getStream().getId())
                .streamName(entity.getStream().getName())
                .build();
    }
}
