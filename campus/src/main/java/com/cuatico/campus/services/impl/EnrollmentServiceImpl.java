package com.cuatico.campus.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cuatico.campus.entities.Certificate;
import com.cuatico.campus.entities.Enrollment;
import com.cuatico.campus.entities.Group;
import com.cuatico.campus.repositories.CertificateRepository;
import com.cuatico.campus.repositories.EnrollmentRepository;
import com.cuatico.campus.repositories.GroupRepository;
import com.cuatico.campus.services.EnrollmentService;
import com.cuatico.campus.services.ServiceException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

	private final GroupRepository groupRepo;
	private final EnrollmentRepository enrollmentRepo;
	private final CertificateRepository certificateRepo;

//  ----------------GESTIÓN DE MATRÍCULAS----------------    
	@Override
	@Transactional
	public Enrollment addEnrollment(Enrollment enrollment) {
	    	
		Group checkedGroup = groupRepo.findById(enrollment.getGroup().getId())
	    		.orElseThrow(() -> new ServiceException("El grupo no existe"));
		Enrollment savedEnrollment = enrollmentRepo.save(enrollment);
	    if (!checkedGroup.getGroupEnrollments().contains(savedEnrollment)) {
	    	savedEnrollment.setGroup(checkedGroup);
	        enrollmentRepo.save(savedEnrollment);
	        checkedGroup.getGroupEnrollments().add(savedEnrollment);
	        groupRepo.save(checkedGroup);

	        if (savedEnrollment.getStudent() != null && !savedEnrollment.getStudent().getStudentEnrollments().contains(savedEnrollment)) {
	        	savedEnrollment.getStudent().getStudentEnrollments().add(savedEnrollment);
	        	return savedEnrollment;
	        }
	    }
	    throw new ServiceException("No se pudo agregar la inscripción correctamente");
	}

//    IMPORTANTE! INTENTAR NO UTILIZAR PORQUE ELIMINA LA TRAZA DE LA MATRICULA. MEJOR UTILIZAR updateEnrollmentStatus

	@Override
	@Transactional
	public void removeEnrollment(Long enrollmentId) {

		Enrollment checkedEnrollment = enrollmentRepo.findById(enrollmentId)
				.orElseThrow(() -> new ServiceException("La matrícula no existe"));
		
		Group checkedGroup = groupRepo.findById(checkedEnrollment.getGroup().getId())
	    		.orElseThrow(() -> new ServiceException("El grupo no existe"));

		if (checkedGroup.getGroupEnrollments().contains(checkedEnrollment)) {
			checkedGroup.getGroupEnrollments().remove(checkedEnrollment);
		}

		if (checkedEnrollment.getStudent() != null) {
			checkedEnrollment.getStudent().getStudentEnrollments().remove(checkedEnrollment);
		}

		checkedEnrollment.setGroup(null);
		enrollmentRepo.delete(checkedEnrollment);
		groupRepo.save(checkedGroup);
	}

//    DEJA LA MATRÍCULA INHABILITADA PERO CONSERVA SU TRAZA EN DB

	@Override
	@Transactional
	public boolean updateEnrollmentStatus(Long enrollmentId, Enrollment.Status newStatus) {
		Enrollment checkedEnrollment = enrollmentRepo.findById(enrollmentId)
				.orElseThrow(() -> new ServiceException("La matrícula no existe"));

		checkedEnrollment.setStatus(newStatus);
		enrollmentRepo.save(checkedEnrollment);
		return true;
	}
	
	@Override
	@Transactional
	public Certificate generateCertificate(Long enrollmentId) {
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
	public List<Enrollment> findAll() {
	    return enrollmentRepo.findAll();
	}

	@Override
	@Transactional
	public Enrollment findById(Long enrollmentId) {
	    return enrollmentRepo.findById(enrollmentId)
	            .orElseThrow(() -> new ServiceException("La matrícula no existe"));
	}


}
