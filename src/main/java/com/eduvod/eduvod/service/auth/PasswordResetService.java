package com.eduvod.eduvod.service.auth;

import com.eduvod.eduvod.dto.auth.PasswordResetRequest;
import com.eduvod.eduvod.dto.auth.PasswordUpdateRequest;

public interface PasswordResetService {
    void requestReset(PasswordResetRequest request);
    void updatePassword(PasswordUpdateRequest request);
}
