package com.cuatico.campus.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import com.cuatico.campus.entities.Certificate;
import com.cuatico.campus.entities.Enrollment;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {

    boolean existsByEnrollment(@NonNull Enrollment enrollment);

    @NonNull Optional<Certificate> findByEnrollment(@NonNull Enrollment enrollment);
    
    @EntityGraph(value = "Certificate.withEnrollment")
    @NonNull Optional<Certificate> findById(@NonNull Long id);
}
