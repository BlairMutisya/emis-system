package com.eduvod.eduvod.controller.superadmin;

import com.eduvod.eduvod.dto.request.superadmin.SchoolContactRequest;
import com.eduvod.eduvod.dto.response.BaseApiResponse;
import com.eduvod.eduvod.dto.response.superadmin.SchoolContactResponse;
import com.eduvod.eduvod.service.superadmin.SchoolContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/superadmin/school-contacts")
@RequiredArgsConstructor
@Tag(name = "School Contacts", description = "Manage contacts of schools")
public class SchoolContactController {

    private final SchoolContactService service;

    @Operation(summary = "Add a school contact", responses = {
            @ApiResponse(responseCode = "200", description = "Contact added", content = @Content(schema = @Schema(implementation = SchoolContactResponse.class)))
    })
    @PostMapping
    public ResponseEntity<BaseApiResponse<SchoolContactResponse>> create(@RequestBody SchoolContactRequest request) {
        SchoolContactResponse response = service.create(request);
        return ResponseEntity.ok(BaseApiResponse.<SchoolContactResponse>builder()
                .statusCode(200)
                .message("School contact added successfully")
                .data(response)
                .build());
    }

    @Operation(summary = "Get contacts by MoE Reg No")
    @GetMapping("/{moeRegNo}")
    public ResponseEntity<BaseApiResponse<List<SchoolContactResponse>>> getBySchool(@PathVariable String moeRegNo) {
        List<SchoolContactResponse> contacts = service.getByMoeRegNo(moeRegNo);
        return ResponseEntity.ok(BaseApiResponse.<List<SchoolContactResponse>>builder()
                .statusCode(200)
                .message("Contacts retrieved")
                .data(contacts)
                .build());
    }
}
