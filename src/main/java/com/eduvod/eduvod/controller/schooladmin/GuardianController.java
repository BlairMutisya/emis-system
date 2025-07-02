package com.eduvod.eduvod.controller.schooladmin;

import com.eduvod.eduvod.dto.request.schooladmin.GuardianRequest;
import com.eduvod.eduvod.dto.response.BaseApiResponse;
import com.eduvod.eduvod.dto.response.schooladmin.GuardianResponse;
import com.eduvod.eduvod.service.schooladmin.GuardianService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schooladmin/guardians")
@RequiredArgsConstructor
public class GuardianController {

    private final GuardianService guardianService;

    @PostMapping
    public BaseApiResponse<GuardianResponse> createGuardian(@RequestBody GuardianRequest request) {
        return guardianService.createGuardian(request);
    }

    @PutMapping("/{id}")
    public BaseApiResponse<GuardianResponse> updateGuardian(@PathVariable Long id,
                                                            @RequestBody GuardianRequest request) {
        return guardianService.updateGuardian(id, request);
    }

    @DeleteMapping("/{id}")
    public BaseApiResponse<Void> deleteGuardian(@PathVariable Long id) {
        return guardianService.deleteGuardian(id);
    }

    @GetMapping
    public BaseApiResponse<List<GuardianResponse>> getAllGuardians() {
        return guardianService.getAllGuardians();
    }
}