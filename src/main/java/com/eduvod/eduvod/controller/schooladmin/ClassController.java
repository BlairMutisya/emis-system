package com.eduvod.eduvod.controller.schooladmin;

import com.eduvod.eduvod.dto.request.schooladmin.CreateClassRequest;
import com.eduvod.eduvod.dto.response.BaseApiResponse;
import com.eduvod.eduvod.dto.response.schooladmin.ClassResponse;
import com.eduvod.eduvod.service.schooladmin.SchoolClassService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//import static com.sun.beans.introspect.PropertyInfo.Name.description;

@RestController
@RequestMapping("/api/v1/schooladmin/classes")
@RequiredArgsConstructor
@Tag(name = "Class Management", description = "School Admin operations for managing classes")
public class ClassController {

    private final SchoolClassService schoolClassService;

    @PostMapping
    @Operation(
            summary = "Create a class for a grade",
            description = "Creates a class associated with a specific grade and academic year",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Class created successfully",
                            content = @Content(schema = @Schema(implementation = BaseApiResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input or grade not found",
                            content = @Content
                    )
            }
    )

    public BaseApiResponse<ClassResponse> createClass(
            @RequestBody CreateClassRequest request) {
        return schoolClassService.createClass(request);
    }

    @GetMapping
    @Operation(
            summary = "Get all classes for school",
            description = "Returns a list of all classes created for the currently logged-in School Admin's school",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of classes returned successfully",
                            content = @Content(schema = @Schema(implementation = BaseApiResponse.class))
                    )
            }
    )
    public BaseApiResponse<List<ClassResponse>> getAllClasses() {
        return schoolClassService.getAllClassesForSchool();
    }
}
