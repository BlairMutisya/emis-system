package com.eduvod.eduvod.service.superadmin;

import com.eduvod.eduvod.dto.response.superadmin.UserResponse;
import com.eduvod.eduvod.enums.UserStatus;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers();
    void changeStatus(Long userId, UserStatus status);
    void softDeleteUser(Long userId);
}
