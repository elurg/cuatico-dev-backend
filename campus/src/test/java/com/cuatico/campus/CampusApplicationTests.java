package com.cuatico.campus;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cuatico.campus.entities.Enrollment;
import com.cuatico.campus.entities.Group;
import com.cuatico.campus.entities.Staff;
import com.cuatico.campus.entities.Student;
import com.cuatico.campus.entities.Teacher;
import com.cuatico.campus.entities.User.Status;
import com.cuatico.campus.repositories.EnrollmentRepository;
import com.cuatico.campus.repositories.GroupRepository;
import com.cuatico.campus.repositories.StudentRepository;
import com.cuatico.campus.repositories.TeacherRepository;

@SpringBootTest
class CampusApplicationTests {
	@Autowired
	private TeacherRepository teacherRepo;
	
	@Autowired
	private GroupRepository groupRepo;
	
	@Autowired
	private StudentRepository studentRepo;

	@Autowired
	private EnrollmentRepository enrollmentRepo;
	
	@Test
	void insertarTeacherYGrupos() {
		
//		CREAR TEACHER
		Teacher teacher = Teacher.builder().email("email@test.com").status(Status.ACTIVE).name("Diana").surnames("Henao").phone("666666666").passwordHash("ÑKJSDHFS6574DF65324.Aa").build();

		
//		CREAR GRUPOS (SI NO HAY GRUPO NO SE LE PUEDE ASIGNAR UN TEACHER)
		Group grupo1 = Group.builder().name("Grupo 1").status(com.cuatico.campus.entities.Group.Status.ACTIVE).build();
		Group grupo2 = Group.builder().name("Grupo 2").status(com.cuatico.campus.entities.Group.Status.ACTIVE).build();
		
		groupRepo.save(grupo1);
		groupRepo.save(grupo2);
		
//		ASIGNAR LOS GRUPOS AL PROFESOR
		List<Group> grupos = new ArrayList<>();
		grupos.add(grupo1);
		grupos.add(grupo2);
		teacher.setGroups(grupos);
		
		
//		INSERTAR EL TEACHER
		
		teacherRepo.save(teacher);
		
		
//		GENERAR UN SET DE TEACHERS (como los cursos pueden tener más de un profe, hay que pasarlos en una colección aunque solo sea uno)
		List<Staff> teachers = new ArrayList<>();
		teachers.add(teacher);

		
//		ASIGNAR EL TEACHER A LOS GRUPOS
		grupo1.setGroupStaff(teachers);
		grupo2.setGroupStaff(teachers);
		
//		ACTUALIZAR LOS GRUPOS CON LA INFO DEL TEACHER
		
		groupRepo.save(grupo1);
		groupRepo.save(grupo2);
		
////		COMPROBAR QUE EXISTE EL TEACHER (con dos grupos) Y LOS GRUPOS
//		Teacher foundTeacher = (Teacher) teacherRepo.findByEmail("email@test.com");
//		assertNotNull(foundTeacher, "El profesor debería existir en la base de datos");
//		
//		assertNotNull(foundTeacher.getTeacherGroups(), "El profesor debería tener grupos asignados");
//	    assertEquals(2, foundTeacher.getTeacherGroups().size(), "El profesor debería tener 2 grupos asignados");
//		
//		Group foundGroup1 = groupRepo.findByName("Grupo 1");
//		assertNotNull(foundGroup1, "El grupo 1 debería existir en la base de datos");
//		Group foundGroup2 = groupRepo.findByName("Grupo 2");
//		assertNotNull(foundGroup2, "El grupo 2 debería existir en la base de datos");
//		
////		COMPROBAR QUE LOS GRUPOS TIENEN TEACHER
//		List<Teacher> foundTeachersGroup1 = groupRepo.findByName("Grupo 1").getGroupTeachers();
//		assertNotNull(foundTeachersGroup1, "El grupo 1 debería existir en la base de datos con un set de teachers");
//		
//		List<Teacher> foundTeachersGroup2 = groupRepo.findByName("Grupo 2").getGroupTeachers();
//		assertNotNull(foundTeachersGroup2, "El grupo 2 debería existir en la base de datos con un set de teachers");
	}	
	
	@Test
	void crearYMatricularStudent() {
		
//		CREAMOS EL ESTUDIANTE Y LO GUARDAMOS EN LA BASE DE DATOS
		Student student = Student.builder().name("Test student").surnames("Testing").email("student@email.com").username("Student1").passwordHash("ÑKJSDHFS6574DF65324.Aa").build();
		studentRepo.save(student);
		
		
//		CREAMOS UN GRUPO EN LA BASE DE DATOS (Este paso es así en los test y no se llama a un grupo ya existente porque no sabemos el orden en el que se lanzan las funciones de un test)
	
		Group groupWithEnrollment = Group.builder().name("Grupo con matrícula").status(com.cuatico.campus.entities.Group.Status.ACTIVE).build();
		groupRepo.save(groupWithEnrollment);
		assertNotNull(groupWithEnrollment, "El Grupo con matrícula debe existir antes de matricular al estudiante");
		
		
//		CREAMOS LA MATRÍCULA DEL ESTUDIANTE EN EL GRUPO Y LA GUARDAMOS EN LA DDBB
		
		Enrollment enroll1 = Enrollment.builder().student(student).group(groupWithEnrollment).build();
		enrollmentRepo.save(enroll1);
		
//		METEMOS LA MATRÍCULA EN UN SET
		List<Enrollment> enrollments = new ArrayList<>();
		enrollments.add(enroll1);
		
		
//		AÑADIMOS LA MATRÍCULA AL GRUPO
		groupWithEnrollment.setGroupEnrollments(enrollments);
		
		
//		GUARDAMOS EL GRUPO CON TODA LA INFORMACIÓN
		groupRepo.save(groupWithEnrollment);
		
//		AÑADIMOS LA MATRÍCULA AL STUDENT
		student.setStudentEnrollments(enrollments);
		
		
//		GUARDAMOS EL STUDENT CON TODA LA INFORMACIÓN
		studentRepo.save(student);
		
		
////      VERIFICAMOS QUE EL ESTUDIANTE EXISTE EN LA BASE DE DATOS
//        Student foundStudent = studentRepo.findByEmail("student@email.com");
//        assertNotNull(foundStudent, "El estudiante debería existir en la base de datos");
//
//        
////      VERIFICAMOS QUE EL ESTUDIANTE TIENE MATRÍCULAS ASIGNADAS
//        assertNotNull(foundStudent.getStudentEnrollments(), "El estudiante debería tener matrículas asociadas");
//        assertEquals(1, foundStudent.getStudentEnrollments().size(), "El estudiante debería tener 1 matrícula");
//
//        
////      VERIFICAMOS QUE LA MATRÍCULA APUNTA AL GRUPO CORRECTO
//        Enrollment enrollment = foundStudent.getStudentEnrollments().iterator().next();
//        assertEquals("Grupo con matrícula", enrollment.getGroup().getName(), "La matrícula debería estar asociada al grupo 1");
//
//        
////      VERIFICAMOS QUE EL GRUPO TIENE MATRÍCULAS REGISTRADAS
//        Group refreshedGroup1 = groupRepo.findByName("Grupo con matrícula");
//        assertNotNull(refreshedGroup1.getGroupEnrollments(), "El grupo debería tener matrículas");
////        assertEquals(1, refreshedGroup1.getGroupEnrollments().size(), "El grupo 1 debería tener 1 matrícula");

	}
}
