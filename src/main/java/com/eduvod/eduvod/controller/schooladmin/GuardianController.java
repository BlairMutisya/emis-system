package com.eduvod.eduvod.controller.schooladmin;

import com.eduvod.eduvod.dto.request.schooladmin.GuardianRequest;
import com.eduvod.eduvod.dto.response.common.BaseApiResponse;
import com.eduvod.eduvod.dto.response.schooladmin.GuardianResponse;
import com.eduvod.eduvod.service.schooladmin.GuardianService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schooladmin/guardians")
@RequiredArgsConstructor
@Tag(name = "Guardian Management", description = "Endpoints for managing guardians")
public class GuardianController {

    private final GuardianService guardianService;

    @Operation(
            summary = "Create a new guardian",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Guardian request payload",
                    required = true,
                    content = @Content(schema = @Schema(implementation = GuardianRequest.class))
            )
    )
    @ApiResponse(
            responseCode = "200",
            description = "Guardian created successfully",
            content = @Content(schema = @Schema(implementation = GuardianResponse.class))
    )
    @PostMapping
    public BaseApiResponse<GuardianResponse> createGuardian(@RequestBody GuardianRequest request) {
        return guardianService.createGuardian(request);
    }

    @Operation(
            summary = "Update a guardian by ID",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Guardian update payload",
                    required = true,
                    content = @Content(schema = @Schema(implementation = GuardianRequest.class))
            )
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Guardian updated successfully",
                    content = @Content(schema = @Schema(implementation = GuardianResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Guardian not found",
                    content = @Content
            )
    })
    @PutMapping("/{id}")
    public BaseApiResponse<GuardianResponse> updateGuardian(
            @Parameter(description = "Guardian ID", required = true)
            @PathVariable Long id,
            @RequestBody GuardianRequest request) {
        return guardianService.updateGuardian(id, request);
    }

    @Operation(
            summary = "Delete a guardian by ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Guardian deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Guardian not found")
    })
    @DeleteMapping("/{id}")
    public BaseApiResponse<Void> deleteGuardian(
            @Parameter(description = "Guardian ID", required = true)
            @PathVariable Long id) {
        return guardianService.deleteGuardian(id);
    }

    @Operation(
            summary = "Get all guardians"
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of guardians retrieved successfully",
            content = @Content(schema = @Schema(implementation = GuardianResponse.class))
    )
    @GetMapping
    public BaseApiResponse<List<GuardianResponse>> getAllGuardians() {
        return guardianService.getAllGuardians();
    }
}
