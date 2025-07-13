package com.eduvod.eduvod.service.email;

import com.eduvod.eduvod.model.shared.User;
import com.eduvod.eduvod.model.superadmin.School;

public interface EmailService {
    void send(String to, String subject, String htmlContent);
    void sendPasswordResetEmail(User user, String token, String code);
    void sendInvitationEmail(User user, String plainPassword);

    void sendSchoolAssignmentEmail(User user, School school);
    void sendSchoolUnassignmentEmail(User user, String schoolName, String moeRegNo, String location);






}
