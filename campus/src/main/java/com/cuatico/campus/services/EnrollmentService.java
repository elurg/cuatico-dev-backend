package com.cuatico.campus.services;

import java.util.List;

import com.cuatico.campus.entities.Certificate;
import com.cuatico.campus.entities.Enrollment;

public interface EnrollmentService {
	Enrollment addEnrollment(Enrollment enrollment);
    void removeEnrollment(Long enrollmentId);
    boolean updateEnrollmentStatus(Long enrollmentId, Enrollment.Status newStatus);
    Certificate generateCertificate(Long enrollmentId);
    List<Enrollment> findAll();
    Enrollment findById(Long enrollmentId);
}
