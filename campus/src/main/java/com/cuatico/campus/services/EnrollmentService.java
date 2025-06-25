package com.cuatico.campus.services;

import com.cuatico.campus.entities.Enrollment;

public interface EnrollmentService {
	Enrollment addEnrollment(Enrollment enrollment);
    void removeEnrollment(Long enrollmentId);
    boolean updateEnrollmentStatus(Long enrollmentId, Enrollment.Status newStatus);
}
