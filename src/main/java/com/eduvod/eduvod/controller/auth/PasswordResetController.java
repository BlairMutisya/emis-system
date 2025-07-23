package com.eduvod.eduvod.controller.auth;

import com.eduvod.eduvod.dto.auth.PasswordChangeRequest;
import com.eduvod.eduvod.dto.auth.PasswordResetRequest;
import com.eduvod.eduvod.dto.auth.PasswordUpdateRequest;
import com.eduvod.eduvod.dto.response.common.BaseApiResponse;
import com.eduvod.eduvod.exception.UnauthorizedActionException;
import com.eduvod.eduvod.security.util.JwtUtil;
import com.eduvod.eduvod.service.auth.PasswordResetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Endpoints for password reset and authentication")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;
    private final JwtUtil jwtUtil;

    public PasswordResetController(PasswordResetService passwordResetService, JwtUtil jwtUtil) {
        this.passwordResetService = passwordResetService;
        this.jwtUtil = jwtUtil;
    }

    @Operation(
            summary = "Request password reset",
            description = "Sends a password reset email with reset link and code to the user's email address.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reset instructions sent to email",
                            content = @Content(schema = @Schema(implementation = BaseApiResponse.class))),
                    @ApiResponse(responseCode = "404", description = "User not found",
                            content = @Content(schema = @Schema(implementation = BaseApiResponse.class)))
            }
    )
    @PostMapping("/forgot-password")
    public ResponseEntity<BaseApiResponse<String>> forgotPassword(@RequestBody PasswordResetRequest request) {
        passwordResetService.requestReset(request);
        return ResponseEntity.ok(BaseApiResponse.success("Reset instructions sent to email."));
    }

    @Operation(
            summary = "Reset user password",
            description = "Updates a user's password using either a reset token (web) or email + reset code (mobile).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Password successfully updated",
                            content = @Content(schema = @Schema(implementation = BaseApiResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid or expired token or code",
                            content = @Content(schema = @Schema(implementation = BaseApiResponse.class)))
            }
    )
    @PostMapping("/reset-password")
    public ResponseEntity<BaseApiResponse<String>> resetPassword(@RequestBody PasswordUpdateRequest request) {
        passwordResetService.updatePassword(request);
        return ResponseEntity.ok(BaseApiResponse.success("Password successfully updated."));
    }

    @Operation(
            summary = "Change password (authenticated)",
            description = "Allows an authenticated user (e.g. on first login) to change password using their current password.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Password changed successfully",
                            content = @Content(schema = @Schema(implementation = BaseApiResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid current password or user not found",
                            content = @Content(schema = @Schema(implementation = BaseApiResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Missing or invalid authorization header",
                            content = @Content(schema = @Schema(implementation = BaseApiResponse.class)))
            }
    )
    @PostMapping("/change-password")
    public ResponseEntity<BaseApiResponse<String>> changePassword(
            @RequestBody PasswordChangeRequest request,
            @RequestHeader("Authorization") String authHeader
    ) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedActionException("Missing or invalid Authorization header");
        }

        String username = jwtUtil.extractUsername(authHeader.replace("Bearer ", ""));
        passwordResetService.changePassword(request, username);

        return ResponseEntity.ok(BaseApiResponse.success("Password changed successfully."));
    }
}
