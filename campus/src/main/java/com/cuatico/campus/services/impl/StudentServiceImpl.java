package com.cuatico.campus.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cuatico.campus.entities.Enrollment;
import com.cuatico.campus.entities.Group;
import com.cuatico.campus.entities.Student;
import com.cuatico.campus.entities.User;
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

		studentRepo.findByEmail(student.getEmail()).ifPresent(s -> {
			throw new ServiceException("El estudiante con email " + student.getEmail() + " ya está registrado.");
		});
		return studentRepo.save(student);
	}

//	----------MATRICULAR ESTUDIANTE-------------

	@Override
	@Transactional
	public Enrollment enrollStudentInGroup(Student student, Group group) {

		Student checkedStudent = studentRepo.findByEmail(student.getEmail()).orElseThrow(
				() -> new ServiceException("El estudiante con email " + student.getEmail() + " no existe."));

		Group checkedGroup = groupRepo.findByName(group.getName())
				.orElseThrow(() -> new ServiceException("El grupo con nombre " + group.getName() + " no existe."));

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

		Student checkedStudent = studentRepo.findByEmail(student.getEmail()).orElseThrow(
				() -> new ServiceException("El estudiante con email " + student.getEmail() + " no existe."));

		Enrollment checkedEnrollment = enrollmentRepo.findById(enrollment.getId()).orElseThrow(
				() -> new ServiceException("La matrícula con el id " + enrollment.getId() + " no existe."));

		if ((checkedStudent.getStudentEnrollments().contains(checkedEnrollment))
				&& checkedStudent.getStudentEnrollments().remove(checkedEnrollment)) {
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
	
	

//	----------CAMBIAR EL STATUS DE LA  MATRÍCULA-------- DEJA LA MATRÍCULA INHABILITADA PERO CONSERVA SU TRAZA EN DB
	
	@Override
	@Transactional
	public boolean updateEnrollmentStatus(Long studentId, Long enrollmentId, Enrollment.Status newStatus) {

		Student checkedStudent = studentRepo.findById(studentId)
				.orElseThrow(() -> new ServiceException("El estudiante no existe"));

		Enrollment checkedEnrollment = enrollmentRepo.findById(enrollmentId)
				.orElseThrow(() -> new ServiceException("La matrícula no existe"));

		if (!checkedStudent.getStudentEnrollments().contains(checkedEnrollment)) {
			return false;
		}
		checkedEnrollment.setStatus(newStatus);
		enrollmentRepo.save(checkedEnrollment);

		return true;
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

//	----------ACTUALIZAR INFO DE ESTUDIANTE POR ID-------------

	@Override
	@Transactional
	public Student updateStudent(Long studentId, Student studentToUpdate) {
		Student existingStudent = studentRepo.findById(studentId)
				.orElseThrow(() -> new ServiceException("El estudiante no existe"));
		existingStudent.setName(studentToUpdate.getName());
		existingStudent.setSurnames(studentToUpdate.getSurnames());
		existingStudent.setUsername(studentToUpdate.getUsername());
		existingStudent.setPhone(studentToUpdate.getPhone());

		if (!existingStudent.getEmail().equals(studentToUpdate.getEmail())) {
			studentRepo.findByEmail(studentToUpdate.getEmail()).ifPresent(s -> {
				throw new ServiceException("El email ya está en uso");
			});
		}
		existingStudent.setEmail(studentToUpdate.getEmail());

		return studentRepo.save(existingStudent);

	}

//	----------CAMBIAR CONTRASEÑA-------------
	
	@Override
	@Transactional
	public void changePassword(Long studentId, String oldPassword, String newPassword) {
		Student checkedStudent = studentRepo.findById(studentId)
				.orElseThrow(() -> new ServiceException("El estudiante no existe"));

//		PRIMERA APROXIMACIÓN PARA NO TENER QUE ACTIVAR SPRING SECURITY AÚN

		if (!checkedStudent.getPasswordHash().equals(oldPassword)) {
			throw new ServiceException("La contraseña actual no es correcta");
		}

		checkedStudent.setPasswordHash(newPassword);

		studentRepo.save(checkedStudent);

//		PARA PRODUCCIÓN
//		if (!passwordEncoder.matches(oldPassword, student.getPasswordHash())) {
//			throw new ServiceException("La contraseña actual no es correcta");
//		}
//		student.setPasswordHash(passwordEncoder.encode(newPassword));
//		studentRepo.save(student);
	}

	
//	----------DESACTIVAR ESTUDIANTE-------------
	@Override
	@Transactional
	public void deactivateStudent(Long id) {
		Student checkedStudent = studentRepo.findById(id)
				.orElseThrow(() -> new ServiceException("El estudiante no existe"));

		checkedStudent.setStatus(User.Status.INACTIVE);
		studentRepo.save(checkedStudent);

	}
	
//	----------LISTAR MATRÍCULAS DE ESTUDIANTE-------------

	@Override
	@Transactional
	public List<Enrollment> getStudentEnrollments(Long studentId) {
		Student checkedStudent = studentRepo.findById(studentId)
				.orElseThrow(() -> new ServiceException("El estudiante no existe"));
		return checkedStudent.getStudentEnrollments();
	}

}
