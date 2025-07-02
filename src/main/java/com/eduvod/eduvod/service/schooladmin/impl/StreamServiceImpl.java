package com.eduvod.eduvod.service.schooladmin.impl;

import com.eduvod.eduvod.dto.request.schooladmin.StreamRequest;
import com.eduvod.eduvod.dto.response.schooladmin.StreamResponse;
import com.eduvod.eduvod.model.schooladmin.Class;
import com.eduvod.eduvod.model.schooladmin.Stream;
import com.eduvod.eduvod.repository.schooladmin.ClassRepository;
import com.eduvod.eduvod.repository.schooladmin.StreamRepository;
import com.eduvod.eduvod.service.schooladmin.StreamService;
import com.eduvod.eduvod.dto.response.BaseApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StreamServiceImpl implements StreamService {

    private final StreamRepository streamRepository;
    private final ClassRepository classRepository;

    @Override
    public BaseApiResponse<StreamResponse> createStream(StreamRequest request) {
        Class clazz = classRepository.findById(request.getClassId())
                .orElseThrow(() -> new RuntimeException("Class not found"));

        Stream stream = Stream.builder()
                .name(request.getName())
                .clazz(clazz)
                .build();

        streamRepository.save(stream);

        return BaseApiResponse.success(mapToResponse(stream));
    }

    @Override
    public BaseApiResponse<List<StreamResponse>> getStreamsByClassId(Long classId) {
        Class clazz = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        List<StreamResponse> streams = streamRepository.findByClazz(clazz).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return BaseApiResponse.success(streams);
    }

    private StreamResponse mapToResponse(Stream stream) {
        return StreamResponse.builder()
                .id(stream.getId())
                .name(stream.getName())
                .className(stream.getClazz().getName())
                .build();
    }
}
