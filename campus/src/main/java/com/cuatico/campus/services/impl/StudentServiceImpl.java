package com.cuatico.campus.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cuatico.campus.entities.Enrollment;
import com.cuatico.campus.entities.Group;
import com.cuatico.campus.entities.Student;
import com.cuatico.campus.repositories.EnrollmentRepository;
import com.cuatico.campus.repositories.GroupRepository;
import com.cuatico.campus.repositories.StudentRepository;
import com.cuatico.campus.services.ServiceException;
import com.cuatico.campus.services.StudentService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

	private final StudentRepository studentRepo;
	private final GroupRepository groupRepo;
	private final EnrollmentRepository enrollmentRepo;

	
//	----------REGISTRAR ESTUDIANTE-------------
	
	@Override
	@Transactional
	public Student registerStudent(Student student) {
		Student checkedStudent = studentRepo.findByEmail(student.getEmail());

		if (checkedStudent != null) {
			throw new ServiceException("El estudiante con email " + student.getEmail() + " ya está registrado.");
		}

		studentRepo.save(checkedStudent);
		return checkedStudent;
	}
	
	

//	----------MATRICULAR ESTUDIANTE-------------

	@Override
	@Transactional
	public Enrollment enrollStudentInGroup(Student student, Group group) {

		Student checkedStudent = studentRepo.findByEmail(student.getEmail());

		if (checkedStudent == null) {
			throw new ServiceException("El estudiante con email " + student.getEmail() + " no existe.");
		}

		Group checkedGroup = groupRepo.findByName(group.getName());

		if (checkedGroup == null) {
			throw new ServiceException("El grupo con nombre " + group.getName() + " no existe.");
		}

		Enrollment enrollment = new Enrollment();

		enrollment.setStudent(checkedStudent);
		enrollment.setGroup(checkedGroup);

		enrollmentRepo.save(enrollment);

		checkedGroup.getGroupEnrollments().add(enrollment);
		groupRepo.save(checkedGroup);

		checkedStudent.getStudentEnrollments().add(enrollment);
		studentRepo.save(checkedStudent);

		return enrollment;
	}
	
	
	
	
//	----------ELIMINAR MATRÍCULA------------- IMPORTANTE! INTENTAR NO UTILIZAR PORQUE ELIMINA LA TRAZA
//	                                          DE LA MATRICULA. MEJOR UTILIZAR updateEnrollmentStatus

	@Override
	@Transactional
	public void removeStudentEnrollment(Student student, Enrollment enrollment) {

		Student checkedStudent = studentRepo.findByEmail(student.getEmail());
		if (checkedStudent == null) {
			throw new ServiceException("El estudiante con email " + student.getEmail() + " no existe.");
		}

		Enrollment checkedEnrollment = enrollmentRepo.findById(enrollment.getId()).orElseThrow(
				() -> new ServiceException("La matrícula con el id " + enrollment.getId() + " no existe."));

		if ((checkedStudent.getStudentEnrollments().contains(checkedEnrollment)) && checkedStudent.getStudentEnrollments().remove(checkedEnrollment)) {
			checkedEnrollment.setStudent(null);
			Group checkedGroup = checkedEnrollment.getGroup();
			if (checkedGroup == null) {
				throw new ServiceException("El grupo relacionado con la matrícula no existe");
			}
			checkedEnrollment.setGroup(null);
			checkedGroup.getGroupEnrollments().remove(checkedEnrollment);
			groupRepo.save(checkedGroup);
	        studentRepo.save(checkedStudent);
	        enrollmentRepo.delete(checkedEnrollment);
		}
	}
	
	
	
//	----------MOSTRAR TODOS LOS ESTUDIANTES-------------

	@Override
	@Transactional
	public List<Student> findAll() {
		List<Student> students = studentRepo.findAll();
		if (students.isEmpty()) {
			throw new ServiceException("No hay estudiantes en la base de datos");
		}
		return students;
	}

	
	
//	----------MOSTRAR ESTUDIANTE POR ID-------------
	
	@Override
	@Transactional
	public Student findById(Long id) {
		Optional<Student> student = studentRepo.findById(id);
		return student.orElseThrow(() -> new ServiceException("El estudiante no existe"));
	}

}
