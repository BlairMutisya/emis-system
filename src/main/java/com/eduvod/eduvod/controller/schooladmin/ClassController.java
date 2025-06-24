package com.eduvod.eduvod.controller.schooladmin;

import com.eduvod.eduvod.dto.request.schooladmin.CreateClassRequest;
import com.eduvod.eduvod.dto.response.BaseApiResponse;
import com.eduvod.eduvod.dto.response.schooladmin.ClassResponse;
import com.eduvod.eduvod.service.schooladmin.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schooladmin/classes")
@RequiredArgsConstructor
public class ClassController {

    private final ClassService classService;

    @PostMapping
    public BaseApiResponse<ClassResponse> createClass(@RequestBody CreateClassRequest request) {
        return classService.createClass(request);
    }

    @GetMapping
    public BaseApiResponse<List<ClassResponse>> getAllClasses() {
        return classService.getAllClassesForSchool();
    }
}
