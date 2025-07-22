package com.eduvod.eduvod.service.schooladmin.impl;

import com.eduvod.eduvod.constants.ErrorMessages;
import com.eduvod.eduvod.dto.request.schooladmin.AssignSubjectToTeacherRequest;
import com.eduvod.eduvod.dto.request.schooladmin.AssignTeacherToStreamRequest;
import com.eduvod.eduvod.dto.response.common.BaseApiResponse;
import com.eduvod.eduvod.dto.response.schooladmin.StreamTeacherResponse;
import com.eduvod.eduvod.exception.StreamNotFoundException;
import com.eduvod.eduvod.exception.StreamTeacherNotFoundException;
import com.eduvod.eduvod.exception.SubjectNotFoundException;
import com.eduvod.eduvod.exception.TeacherNotFoundException;
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
    private final StaffRepository staffRepository;
    private final StreamTeacherRepository streamTeacherRepository;
    private final SubjectRepository subjectRepository;
    private final StreamTeacherSubjectRepository streamTeacherSubjectRepository;

    @Override
    public BaseApiResponse<StreamTeacherResponse> assignTeacherToStream(AssignTeacherToStreamRequest request) {
        Stream stream = streamRepository.findById(request.getStreamId())
                .orElseThrow(() -> new StreamNotFoundException(ErrorMessages.STREAM_NOT_FOUND));

        Staff staff = staffRepository.findById(request.getStaffId())
                .filter(Staff::isTeacher)
                .orElseThrow(() -> new TeacherNotFoundException(ErrorMessages.TEACHER_NOT_FOUND));

        StreamTeacher streamTeacher = StreamTeacher.builder()
                .stream(stream)
                .staff(staff)
                .deleted(false)
                .build();

        streamTeacher = streamTeacherRepository.save(streamTeacher);

        return BaseApiResponse.success(mapToResponse(streamTeacher));
    }

    @Override
    public BaseApiResponse<String> assignSubjectsToStreamTeacher(AssignSubjectToTeacherRequest request) {
        StreamTeacher streamTeacher = streamTeacherRepository.findById(request.getStreamTeacherId())
                .orElseThrow(() -> new StreamTeacherNotFoundException(ErrorMessages.STREAM_TEACHER_NOT_FOUND));

        for (Long subjectId : request.getSubjectIds()) {
            Subject subject = subjectRepository.findById(subjectId)
                    .orElseThrow(() -> new SubjectNotFoundException(ErrorMessages.SUBJECT_NOT_FOUND));

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
                .staffId(entity.getStaff().getId())
                .staffName(entity.getStaff().getFirstName() + " " + entity.getStaff().getLastName())
                .streamId(entity.getStream().getId())
                .streamName(entity.getStream().getName())
                .build();
    }
}

