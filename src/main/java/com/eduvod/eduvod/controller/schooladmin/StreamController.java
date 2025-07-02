package com.eduvod.eduvod.controller.schooladmin;

import com.eduvod.eduvod.dto.request.schooladmin.StreamRequest;
import com.eduvod.eduvod.dto.response.schooladmin.StreamResponse;
import com.eduvod.eduvod.service.schooladmin.StreamService;
import com.eduvod.eduvod.dto.response.BaseApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schooladmin/streams")
@RequiredArgsConstructor
public class StreamController {

    private final StreamService streamService;

    @PostMapping
    public BaseApiResponse<StreamResponse> createStream(@RequestBody StreamRequest request) {
        return streamService.createStream(request);
    }

    @GetMapping("/by-class/{classId}")
    public BaseApiResponse<List<StreamResponse>> getStreamsByClass(@PathVariable Long classId) {
        return streamService.getStreamsByClassId(classId);
    }
}
