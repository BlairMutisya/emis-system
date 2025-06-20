package com.eduvod.eduvod.controller.auth;

import com.eduvod.eduvod.dto.auth.AuthenticationRequest;
import com.eduvod.eduvod.dto.auth.AuthenticationResponse;
import com.eduvod.eduvod.dto.auth.RegisterRequest;
import com.eduvod.eduvod.dto.response.BaseApiResponse;
import com.eduvod.eduvod.security.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/superadmin")
@RequiredArgsConstructor
@Tag(name = "Super Admin Authentication", description = "Endpoints for super admin registration and login")
public class SuperAdminAuthController {

    private final AuthenticationService authService;

    @Operation(
            summary = "Register a new Super Admin",
            description = "Creates a new super admin account and returns a JWT token",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully registered",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AuthenticationResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request",
                            content = @Content
                    )
            }
    )
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.registerSuperAdmin(request));
    }

    @Operation(
            summary = "Super Admin Login",
            description = "Authenticates a super admin and returns a JWT token wrapped in a base API response",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful login",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BaseApiResponse.class))
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<BaseApiResponse<AuthenticationResponse>> login(
            @RequestBody AuthenticationRequest request) {

        AuthenticationResponse authResponse = authService.authenticate(request);

        return ResponseEntity.ok(
                BaseApiResponse.<AuthenticationResponse>builder()
                        .statusCode(200)
                        .message("Login successful")
                        .data(authResponse)
                        .build()
        );
    }
}
