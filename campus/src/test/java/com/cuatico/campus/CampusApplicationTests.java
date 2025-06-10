package com.cuatico.campus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cuatico.campus.entities.Group;
import com.cuatico.campus.entities.Teacher;
import com.cuatico.campus.entities.User.Roles;
import com.cuatico.campus.entities.User.Status;
import com.cuatico.campus.repositories.GroupRepository;
import com.cuatico.campus.repositories.TeacherRepository;

@SpringBootTest
class CampusApplicationTests {
	@Autowired
	private TeacherRepository teacherRepo;
	
	@Autowired
	private GroupRepository groupRepo;

	
	@Test
	void insertarTeacherYGrupos() {
		
//		CREAR TEACHER
		Teacher teacher = Teacher.builder().email("email@test.com").status(Status.ACTIVE).name("Diana").surnames("Henao").phone("666666666").passwordHash("ÑKJSDHFS6574DF65324.Aa").roles(Set.of(Roles.TEACHER)).build();

		
//		CREAR GRUPOS (SI NO HAY GRUPO NO SE LE PUEDE ASIGNAR UN TEACHER)
		Group grupo1 = Group.builder().name("Grupo 1").status(com.cuatico.campus.entities.Group.Status.ACTIVE).build();
		Group grupo2 = Group.builder().name("Grupo 2").status(com.cuatico.campus.entities.Group.Status.ACTIVE).build();
		
		groupRepo.save(grupo1);
		groupRepo.save(grupo2);
		
//		ASIGNAR LOS GRUPOS AL PROFESOR
		Set<Group> grupos = new HashSet<>();
		grupos.add(grupo1);
		grupos.add(grupo2);
		teacher.setTeacherGroups(grupos);
		
		
//		INSERTAR EL TEACHER
		
		teacherRepo.save(teacher);
		
		
//		GENERAR UN SET DE TEACHERS (como los cursos pueden tener más de un profe, hay que pasarlos en una colección aunque solo sea uno)
		Set<Teacher> teachers = new HashSet<Teacher>();
		teachers.add(teacher);

		
//		ASIGNAR EL TEACHER A LOS GRUPOS
		grupo1.setGroupTeachers(teachers);
		grupo2.setGroupTeachers(teachers);
		
//		ACTUALIZAR LOS GRUPOS CON LA INFO DEL TEACHER
		
		groupRepo.save(grupo1);
		groupRepo.save(grupo2);
		
//		COMPROBAR QUE EXISTE EL TEACHER (con dos grupos) Y LOS GRUPOS
		Teacher foundTeacher = (Teacher) teacherRepo.findByEmail("email@test.com");
		assertNotNull(foundTeacher, "El profesor debería existir en la base de datos");
		
		assertNotNull(foundTeacher.getTeacherGroups(), "El profesor debería tener grupos asignados");
	    assertEquals(2, foundTeacher.getTeacherGroups().size(), "El profesor debería tener 2 grupos asignados");
		
		Group foundGroup1 = groupRepo.findByName("Grupo 1");
		assertNotNull(foundGroup1, "El grupo 1 debería existir en la base de datos");
		Group foundGroup2 = groupRepo.findByName("Grupo 2");
		assertNotNull(foundGroup2, "El grupo 2 debería existir en la base de datos");
		
//		COMPROBAR QUE LOS GRUPOS TIENEN TEACHER
		Set<Teacher> foundTeachersGroup1 = groupRepo.findByName("Grupo 1").getGroupTeachers();
		assertNotNull(foundTeachersGroup1, "El grupo 1 debería existir en la base de datos con un set de teachers");
		
		Set<Teacher> foundTeachersGroup2 = groupRepo.findByName("Grupo 2").getGroupTeachers();
		assertNotNull(foundTeachersGroup2, "El grupo 2 debería existir en la base de datos con un set de teachers");
	}	
}
