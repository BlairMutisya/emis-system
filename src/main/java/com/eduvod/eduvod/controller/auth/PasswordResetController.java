package com.eduvod.eduvod.controller.auth;

import com.eduvod.eduvod.dto.auth.PasswordResetRequest;
import com.eduvod.eduvod.dto.auth.PasswordUpdateRequest;
import com.eduvod.eduvod.dto.response.common.BaseApiResponse;
import com.eduvod.eduvod.service.auth.PasswordResetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Endpoints for password reset and authentication")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    public PasswordResetController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }


    @Operation(
            summary = "Request password reset",
            description = "Sends a password reset email with reset link and code to the user's email address.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reset instructions sent to email"),
                    @ApiResponse(responseCode = "404", description = "User not found",
                            content = @Content(schema = @Schema(implementation = BaseApiResponse.class)))
            }
    )
    @PostMapping("/forgot-password")
    public ResponseEntity<BaseApiResponse<Void>> forgotPassword(@RequestBody PasswordResetRequest request) {
        passwordResetService.requestReset(request);
        return ResponseEntity.ok(new BaseApiResponse<>(200, "Reset instructions sent to email."));
    }

    @Operation(
            summary = "Reset user password",
            description = "Updates a user's password using either a reset token (web) or email + reset code (mobile).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Password successfully updated"),
                    @ApiResponse(responseCode = "400", description = "Invalid or expired token or code",
                            content = @Content(schema = @Schema(implementation = BaseApiResponse.class)))
            }
    )
    @PostMapping("/reset-password")
    public ResponseEntity<BaseApiResponse<Void>> resetPassword(@RequestBody PasswordUpdateRequest request) {
        passwordResetService.updatePassword(request);
        return ResponseEntity.ok(new BaseApiResponse<>(200, "Password successfully updated."));
    }
}
