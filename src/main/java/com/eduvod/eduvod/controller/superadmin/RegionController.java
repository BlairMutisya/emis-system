package com.eduvod.eduvod.controller.superadmin;

import com.eduvod.eduvod.dto.request.superadmin.RegionRequest;
import com.eduvod.eduvod.dto.response.BaseApiResponse;
import com.eduvod.eduvod.dto.response.superadmin.RegionResponse;
import com.eduvod.eduvod.dto.response.superadmin.CountyResponse;
import com.eduvod.eduvod.service.superadmin.RegionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/superadmin/regions")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @Operation(summary = "Create a new Region")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Region created successfully"))
    @PostMapping
    public ResponseEntity<BaseApiResponse<RegionResponse>> createRegion(@RequestBody RegionRequest request) {
        return ResponseEntity.ok(regionService.createRegion(request));
    }

    @Operation(summary = "Update a Region")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Region updated successfully"))
    @PutMapping("/{id}")
    public ResponseEntity<BaseApiResponse<RegionResponse>> updateRegion(
            @PathVariable Long id,
            @RequestBody RegionRequest request
    ) {
        return ResponseEntity.ok(regionService.updateRegion(id, request));
    }

    @Operation(summary = "Delete a Region")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Region deleted successfully"))
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseApiResponse<String>> deleteRegion(@PathVariable Long id) {
        return ResponseEntity.ok(regionService.deleteRegion(id));
    }

    @Operation(summary = "Get a Region by ID")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Region fetched successfully"))
    @GetMapping("/{id}")
    public ResponseEntity<BaseApiResponse<RegionResponse>> getByRegionId(@PathVariable Long id) {
        return ResponseEntity.ok(regionService.getRegionById(id));
    }

    @Operation(summary = "Get all Regions")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Regions fetched successfully"))
    @GetMapping
    public ResponseEntity<BaseApiResponse<List<RegionResponse>>> getAllRegions() {
        return ResponseEntity.ok(regionService.getAllRegions());
    }

    @Operation(summary = "Get Counties by Region ID")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Counties fetched successfully"))
    @GetMapping("/{regionId}/counties")
    public ResponseEntity<BaseApiResponse<List<CountyResponse>>> getCountiesByRegionId(@PathVariable Long regionId) {
        return ResponseEntity.ok(regionService.getCountiesByRegionId(regionId));
    }
}
