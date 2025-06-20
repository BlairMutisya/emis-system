package com.eduvod.eduvod.controller.superadmin;

import com.eduvod.eduvod.dto.request.superadmin.TypeRequest;
import com.eduvod.eduvod.dto.response.BaseApiResponse;
import com.eduvod.eduvod.dto.response.superadmin.TypeResponse;
import com.eduvod.eduvod.service.superadmin.SchoolTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/superadmin/school-types")
@RequiredArgsConstructor
@Tag(name = "School Types", description = "Super Admin - Manage school types")
public class SchoolTypeController {

    private final SchoolTypeService service;

    @PostMapping
    public ResponseEntity<BaseApiResponse<TypeResponse>> create(@RequestBody TypeRequest request) {
        TypeResponse response = service.create(request);
        return ResponseEntity.ok(BaseApiResponse.<TypeResponse>builder()
                .statusCode(200)
                .message("School type created")
                .data(response)
                .build());
    }

    @GetMapping
    public ResponseEntity<BaseApiResponse<List<TypeResponse>>> getAll() {
        return ResponseEntity.ok(BaseApiResponse.<List<TypeResponse>>builder()
                .statusCode(200)
                .message("List of school types")
                .data(service.getAll())
                .build());
    }
}