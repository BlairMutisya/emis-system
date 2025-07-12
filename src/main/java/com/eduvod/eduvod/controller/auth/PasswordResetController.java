package com.eduvod.eduvod.controller.auth;

import com.eduvod.eduvod.dto.auth.PasswordResetRequest;
import com.eduvod.eduvod.dto.auth.PasswordUpdateRequest;
import com.eduvod.eduvod.dto.response.BaseApiResponse;
import com.eduvod.eduvod.service.auth.PasswordResetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Password Reset", description = "Endpoints for requesting and resetting passwords")
public class PasswordResetController {

    private final PasswordResetService resetService;

    @Operation(
            summary = "Request password reset",
            description = "Send a password reset link/token to the user's email"
    )
    @ApiResponse(responseCode = "200", description = "Reset link sent successfully")
    @PostMapping("/request-reset")
    public ResponseEntity<BaseApiResponse<String>> requestReset(@RequestBody PasswordResetRequest request) {
        resetService.requestReset(request);
        return ResponseEntity.ok(
                new BaseApiResponse<>(200, "Reset link sent to your email", null)
        );
    }

    @Operation(
            summary = "Reset password using token",
            description = "Submit token and new password to update user credentials"
    )
    @ApiResponse(responseCode = "200", description = "Password reset successfully")
    @PostMapping("/reset-password")
    public ResponseEntity<BaseApiResponse<String>> updatePassword(@RequestBody PasswordUpdateRequest request) {
        resetService.updatePassword(request);
        return ResponseEntity.ok(
                new BaseApiResponse<>(200, "Password updated successfully", null)
        );
    }
}
