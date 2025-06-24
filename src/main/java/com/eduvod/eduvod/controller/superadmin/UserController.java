package com.eduvod.eduvod.controller.superadmin;

import com.eduvod.eduvod.dto.response.BaseApiResponse;
import com.eduvod.eduvod.dto.response.superadmin.UserResponse;
import com.eduvod.eduvod.enums.UserStatus;
import com.eduvod.eduvod.service.superadmin.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/superadmin/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get all users with status, roles, and school info (if any)")
    @GetMapping
    public ResponseEntity<BaseApiResponse<List<UserResponse>>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(new BaseApiResponse<>(200, "Fetched all users", users));
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<BaseApiResponse<Void>> changeUserStatus(
            @PathVariable Long id,
            @RequestParam UserStatus status) {
        userService.changeStatus(id, status);
        return ResponseEntity.ok(new BaseApiResponse<>(200, "User status updated"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseApiResponse<Void>> softDeleteUser(@PathVariable Long id) {
        userService.softDeleteUser(id);
        return ResponseEntity.ok(new BaseApiResponse<>(200, "User soft-deleted successfully"));
    }
}

