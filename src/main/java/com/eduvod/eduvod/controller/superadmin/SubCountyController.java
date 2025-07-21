package com.eduvod.eduvod.controller.superadmin;

import com.eduvod.eduvod.dto.request.superadmin.SubCountyRequest;
import com.eduvod.eduvod.dto.response.common.BaseApiResponse;
import com.eduvod.eduvod.dto.response.superadmin.SubCountyResponse;
import com.eduvod.eduvod.service.superadmin.SubCountyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/superadmin/subcounties")
@RequiredArgsConstructor
public class SubCountyController {

    private final SubCountyService subCountyService;

    @Operation(summary = "Create a new SubCounty")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "SubCounty created successfully"))
    @PostMapping
    public ResponseEntity<BaseApiResponse<SubCountyResponse>> createSubCounty(@RequestBody SubCountyRequest request) {
        return ResponseEntity.ok(subCountyService.createSubCounty(request));
    }

    @Operation(summary = "Update a SubCounty")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "SubCounty updated successfully"))
    @PutMapping("/{id}")
    public ResponseEntity<BaseApiResponse<SubCountyResponse>> updateSubCounty(
            @PathVariable Long id,
            @RequestBody SubCountyRequest request
    ) {
        return ResponseEntity.ok(subCountyService.updateSubCounty(id, request));
    }

    @Operation(summary = "Delete a SubCounty")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "SubCounty deleted successfully"))
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseApiResponse<String>> deleteSubCounty(@PathVariable Long id) {
        return ResponseEntity.ok(subCountyService.deleteSubCounty(id));
    }

    @Operation(summary = "Get SubCounty by ID")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "SubCounty fetched successfully"))
    @GetMapping("/{id}")
    public ResponseEntity<BaseApiResponse<SubCountyResponse>> getBySubCountyId(@PathVariable Long id) {
        return ResponseEntity.ok(subCountyService.getBySubCountyId(id));
    }

    @Operation(summary = "Get all SubCounties")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "SubCounties fetched successfully"))
    @GetMapping
    public ResponseEntity<BaseApiResponse<List<SubCountyResponse>>> getAllSubCounties() {
        return ResponseEntity.ok(subCountyService.getAllSubCounties());
    }

    @Operation(summary = "Get SubCounties by County ID")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "SubCounties fetched successfully"))
    @GetMapping("/by-county/{countyId}")
    public ResponseEntity<BaseApiResponse<List<SubCountyResponse>>> getByCountyId(@PathVariable Long countyId) {
        return ResponseEntity.ok(subCountyService.getByCountyId(countyId));
    }
}
