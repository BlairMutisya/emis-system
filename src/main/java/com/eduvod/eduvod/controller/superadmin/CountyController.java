package com.eduvod.eduvod.controller.superadmin;

import com.eduvod.eduvod.dto.request.superadmin.CountyRequest;
import com.eduvod.eduvod.dto.response.BaseApiResponse;
import com.eduvod.eduvod.dto.response.superadmin.CountyResponse;
import com.eduvod.eduvod.dto.response.superadmin.SubCountyResponse;
import com.eduvod.eduvod.service.superadmin.CountyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/superadmin/counties")
@RequiredArgsConstructor
public class CountyController {

    private final CountyService countyService;

    @Operation(summary = "Create a new County")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "County created successfully"))
    @PostMapping
    public ResponseEntity<BaseApiResponse<CountyResponse>> createCounty(@RequestBody CountyRequest request) {
        return ResponseEntity.ok(countyService.createCounty(request));
    }

    @Operation(summary = "Update a County")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "County updated successfully"))
    @PutMapping("/{id}")
    public ResponseEntity<BaseApiResponse<CountyResponse>> updateCounty(
            @PathVariable Long id,
            @RequestBody CountyRequest request
    ) {
        return ResponseEntity.ok(countyService.updateCounty(id, request));
    }

    @Operation(summary = "Delete a County")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "County deleted successfully"))
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseApiResponse<String>> deleteCounty(@PathVariable Long id) {
        return ResponseEntity.ok(countyService.deleteCounty(id));
    }

    @Operation(summary = "Get County by ID")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "County fetched successfully"))
    @GetMapping("/{id}")
    public ResponseEntity<BaseApiResponse<CountyResponse>> getByCountyId(@PathVariable Long id) {
        return ResponseEntity.ok(countyService.getByCountyId(id));
    }

    @Operation(summary = "Get all Counties")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Counties fetched successfully"))
    @GetMapping
    public ResponseEntity<BaseApiResponse<List<CountyResponse>>> getAllCounties() {
        return ResponseEntity.ok(countyService.getAllCounties());
    }

    @Operation(summary = "Get SubCounties by County ID")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "SubCounties fetched successfully"))
    @GetMapping("/{countyId}/subcounties")
    public ResponseEntity<BaseApiResponse<List<SubCountyResponse>>> getSubCountiesByCountyId(@PathVariable Long countyId) {
        return ResponseEntity.ok(countyService.getSubCountiesByCountyId(countyId));
    }
}
