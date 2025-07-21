package com.eduvod.eduvod.controller.schooladmin;

import com.eduvod.eduvod.dto.request.schooladmin.StreamRequest;
import com.eduvod.eduvod.dto.response.schooladmin.StreamResponse;
import com.eduvod.eduvod.dto.response.common.BaseApiResponse;
import com.eduvod.eduvod.service.schooladmin.StreamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schooladmin/streams")
@RequiredArgsConstructor
@Tag(name = "Streams", description = "Endpoints for managing class streams")
public class StreamController {

    private final StreamService streamService;

    @Operation(summary = "Create a new stream", description = "Creates a new stream for a given class")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stream created successfully",
                    content = @Content(schema = @Schema(implementation = StreamResponse.class)))
    })
    @PostMapping
    public BaseApiResponse<StreamResponse> createStream(
            @RequestBody StreamRequest request
    ) {
        return streamService.createStream(request);
    }

    @Operation(summary = "Get streams by class ID", description = "Returns a list of streams for a specific class")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Streams retrieved successfully",
                    content = @Content(schema = @Schema(implementation = StreamResponse.class)))
    })
    @GetMapping("/by-class/{classId}")
    public BaseApiResponse<List<StreamResponse>> getStreamsByClass(
            @PathVariable Long classId
    ) {
        return streamService.getStreamsByClassId(classId);
    }
}
