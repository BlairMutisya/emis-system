package com.eduvod.eduvod.controller.schooladmin;

import com.eduvod.eduvod.dto.request.schooladmin.AssignSubjectToStreamTeacherRequest;
import com.eduvod.eduvod.dto.request.schooladmin.AssignTeacherToStreamRequest;
import com.eduvod.eduvod.dto.response.BaseApiResponse;
import com.eduvod.eduvod.dto.response.schooladmin.StreamTeacherResponse;
import com.eduvod.eduvod.service.schooladmin.StreamTeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/school-admin/stream-teachers")
@RequiredArgsConstructor
public class StreamTeacherController {

    private final StreamTeacherService streamTeacherService;

    @PostMapping("/assign")
    public ResponseEntity<BaseApiResponse<StreamTeacherResponse>> assignTeacherToStream(@RequestBody AssignTeacherToStreamRequest request) {
        return ResponseEntity.ok(streamTeacherService.assignTeacherToStream(request));
    }

    @PostMapping("/assign-subjects")
    public ResponseEntity<BaseApiResponse<String>> assignSubjectsToTeacher(@RequestBody AssignSubjectToStreamTeacherRequest request) {
        return ResponseEntity.ok(streamTeacherService.assignSubjectsToStreamTeacher(request));
    }

    @GetMapping("/by-stream/{streamId}")
    public ResponseEntity<BaseApiResponse<List<StreamTeacherResponse>>> getTeachersByStream(@PathVariable Long streamId) {
        return ResponseEntity.ok(streamTeacherService.getTeachersByStream(streamId));
    }
}
