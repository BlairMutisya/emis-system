package com.eduvod.eduvod.controller.schooladmin;

import com.eduvod.eduvod.dto.request.schooladmin.AssignSubjectToTeacherRequest;
import com.eduvod.eduvod.dto.request.schooladmin.AssignTeacherToStreamRequest;
import com.eduvod.eduvod.dto.response.common.BaseApiResponse;
import com.eduvod.eduvod.dto.response.schooladmin.StreamTeacherResponse;
import com.eduvod.eduvod.service.schooladmin.StreamTeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schooladmin/teachers")
@RequiredArgsConstructor
@Tag(name = "Teacher Management", description = "Endpoints for assigning teachers and subjects to streams")
public class StreamTeacherController {

    private final StreamTeacherService streamTeacherService;

    @Operation(summary = "Assign a teacher to a stream")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teacher assigned successfully",
                    content = @Content(schema = @Schema(implementation = StreamTeacherResponse.class)))
    })
    @PostMapping("/assign")
    public ResponseEntity<BaseApiResponse<StreamTeacherResponse>> assignTeacherToStream(
            @RequestBody AssignTeacherToStreamRequest request) {
        return ResponseEntity.ok(streamTeacherService.assignTeacherToStream(request));
    }

    @Operation(summary = "Assign subjects to a teacher in a stream")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subjects assigned successfully",
                    content = @Content(schema = @Schema(implementation = BaseApiResponse.class)))
    })
    @PostMapping("/assign-subjects")
    public ResponseEntity<BaseApiResponse<String>> assignSubjectsToTeacher(
            @RequestBody AssignSubjectToTeacherRequest request) {
        return ResponseEntity.ok(streamTeacherService.assignSubjectsToStreamTeacher(request));
    }

    @Operation(summary = "Get teachers assigned to a stream")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teachers fetched successfully",
                    content = @Content(schema = @Schema(implementation = StreamTeacherResponse.class)))
    })
    @GetMapping("/by-stream/{streamId}")
    public ResponseEntity<BaseApiResponse<List<StreamTeacherResponse>>> getTeachersByStream(
            @PathVariable Long streamId) {
        return ResponseEntity.ok(streamTeacherService.getTeachersByStream(streamId));
    }
}
