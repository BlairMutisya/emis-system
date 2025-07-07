package com.eduvod.eduvod.controller.superadmin;

import com.eduvod.eduvod.dto.response.BaseApiResponse;
import com.eduvod.eduvod.dto.response.superadmin.UserResponse;
import com.eduvod.eduvod.enums.UserStatus;
import com.eduvod.eduvod.service.superadmin.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/superadmin/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "Super Admin - Manage system users")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Get all users",
            description = "Fetches all users including roles, status (active/blocked), and school info for school admins.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Users fetched successfully",
                            content = @Content(schema = @Schema(implementation = UserResponse.class)))
            }
    )
    @GetMapping
    public ResponseEntity<BaseApiResponse<List<UserResponse>>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(new BaseApiResponse<>(200, "Fetched all users", users));
    }

    @Operation(
            summary = "Change user status",
            description = "Change the status (ACTIVE/BLOCKED) of a user by ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User status updated"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @PutMapping("/{id}/status")
    public ResponseEntity<BaseApiResponse<Void>> changeUserStatus(
            @PathVariable Long id,
            @RequestParam UserStatus status) {
        userService.changeStatus(id, status);
        return ResponseEntity.ok(new BaseApiResponse<>(200, "User status updated"));
    }

    @Operation(
            summary = "Soft delete user",
            description = "Soft deletes a user by setting the deletedAt field instead of permanently removing the record.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User soft-deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseApiResponse<Void>> softDeleteUser(@PathVariable Long id) {
        userService.softDeleteUser(id);
        return ResponseEntity.ok(new BaseApiResponse<>(200, "User soft-deleted successfully"));
    }
}
