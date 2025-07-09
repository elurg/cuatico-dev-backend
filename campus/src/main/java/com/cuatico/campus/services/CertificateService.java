package com.cuatico.campus.services;

import com.cuatico.campus.entities.Certificate;

public interface CertificateService {
    Certificate createCertificate(Long enrollmentId);
    Certificate findById(Long certificateId);
    Certificate findByEnrollment(Long enrollmentId);
}
