package com.cuatico.campus.services.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cuatico.campus.entities.Certificate;
import com.cuatico.campus.entities.Enrollment;
import com.cuatico.campus.repositories.CertificateRepository;
import com.cuatico.campus.repositories.EnrollmentRepository;
import com.cuatico.campus.services.CertificateService;
import com.cuatico.campus.services.ServiceException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepo;
    private final EnrollmentRepository enrollmentRepo;

    @Override
    @Transactional
    public Certificate createCertificate(Long enrollmentId) {
        Enrollment enrollment = enrollmentRepo.findById(enrollmentId)
            .orElseThrow(() -> new ServiceException("La matrícula no existe"));

        if (certificateRepo.existsByEnrollment(enrollment)) {
            throw new ServiceException("Ya existe un certificado para esta matrícula");
        }

        String generatedCode = "CERT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Certificate certificate = Certificate.builder()
            .enrollment(enrollment)
            .certificateCode(generatedCode)
            .certificationDate(LocalDateTime.now())
            .build();

        return certificateRepo.save(certificate);
    }

    @Override
    @Transactional
    public Certificate findById(Long certificateId) {
        return certificateRepo.findById(certificateId)
            .orElseThrow(() -> new ServiceException("El certificado no existe"));
    }

    @Override
    @Transactional
    public Certificate findByEnrollment(Long enrollmentId) {
        Enrollment enrollment = enrollmentRepo.findById(enrollmentId)
            .orElseThrow(() -> new ServiceException("La matrícula no existe"));
        return certificateRepo.findByEnrollment(enrollment)
            .orElseThrow(() -> new ServiceException("No existe certificado para esta matrícula"));
    }
}