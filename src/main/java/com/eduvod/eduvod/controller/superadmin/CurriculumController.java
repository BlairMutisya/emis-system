package com.eduvod.eduvod.controller.superadmin;

import com.eduvod.eduvod.dto.request.superadmin.TypeRequest;
import com.eduvod.eduvod.dto.response.BaseApiResponse;
import com.eduvod.eduvod.dto.response.superadmin.TypeResponse;
import com.eduvod.eduvod.service.superadmin.CurriculumTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/superadmin/curriculum")
@RequiredArgsConstructor
@Tag(name = "Super Admin - Curriculum Management", description = "Endpoints for managing curriculum types")
public class CurriculumController {

    private final CurriculumTypeService service;

    @Operation(summary = "Create a new curriculum type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Curriculum created successfully",
                    content = @Content(schema = @Schema(implementation = TypeResponse.class)))
    })
    @PostMapping
    public ResponseEntity<BaseApiResponse<TypeResponse>> create(@RequestBody TypeRequest request) {
        TypeResponse response = service.create(request);
        return ResponseEntity.ok(BaseApiResponse.<TypeResponse>builder()
                .statusCode(200)
                .message("Curriculum created")
                .data(response)
                .build());
    }

    @Operation(summary = "Get all curriculum types")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of curriculums retrieved successfully",
                    content = @Content(schema = @Schema(implementation = TypeResponse.class)))
    })
    @GetMapping
    public ResponseEntity<BaseApiResponse<List<TypeResponse>>> getAll() {
        return ResponseEntity.ok(BaseApiResponse.<List<TypeResponse>>builder()
                .statusCode(200)
                .message("List of curriculums")
                .data(service.getAll())
                .build());
    }
}
