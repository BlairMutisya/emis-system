package com.eduvod.eduvod.service.superadmin;

import com.eduvod.eduvod.dto.response.superadmin.UserResponse;
import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers();
}
