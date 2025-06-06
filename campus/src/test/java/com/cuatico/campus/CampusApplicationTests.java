package com.cuatico.campus;

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
import com.cuatico.campus.repositories.UserRepository;

@SpringBootTest
class CampusApplicationTests {
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private GroupRepository groupRepo;

	@Test
	void insertarUser() {
		Teacher teacher = Teacher.builder().email("email@test.com").status(Status.ACTIVE).name("Diana").surnames("Henao").phone("666666666").passwordHash("ÑKJSDHFS6574DF65324.Aa").roles(Set.of(Roles.TEACHER)).build();

		Set<Group> grupos = new HashSet<>();
		Group grupo1 = Group.builder().name("Grupo 1").status(com.cuatico.campus.entities.Group.Status.ACTIVE).build();
		Group grupo2 = Group.builder().name("Grupo 2").status(com.cuatico.campus.entities.Group.Status.ACTIVE).build();
		grupos.add(grupo1);
		grupos.add(grupo2);
		
		teacher.setAssignedGroups(grupos);
		
		Set<Teacher> teachers = new HashSet<Teacher>();
		teachers.add(teacher);
		
		repo.save(teacher);
		
		grupo1.setTeachers(teachers);
		grupo2.setTeachers(teachers);
		
		groupRepo.save(grupo1);
		groupRepo.save(grupo2);
		

		Teacher foundTeacher = (Teacher) repo.findByEmail("email@test.com");
		assertNotNull(foundTeacher, "El profesor debería existir en la base de datos");
	}

}
