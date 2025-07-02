package com.eduvod.eduvod.controller.schooladmin;

import com.eduvod.eduvod.dto.request.schooladmin.SubjectRequest;
import com.eduvod.eduvod.dto.response.BaseApiResponse;
import com.eduvod.eduvod.dto.response.schooladmin.SubjectResponse;
import com.eduvod.eduvod.service.schooladmin.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/school-admin/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping
    public BaseApiResponse<SubjectResponse> createSubject(@RequestBody SubjectRequest request) {
        return subjectService.createSubject(request);
    }

    @PutMapping("/{id}")
    public BaseApiResponse<SubjectResponse> updateSubject(@PathVariable Long id, @RequestBody SubjectRequest request) {
        return subjectService.updateSubject(id, request);
    }

    @DeleteMapping("/{id}")
    public BaseApiResponse<Void> deleteSubject(@PathVariable Long id) {
        return subjectService.deleteSubject(id);
    }

    @GetMapping
    public BaseApiResponse<List<SubjectResponse>> getAllSubjects() {
        return subjectService.getAllSubjects();
    }
}
