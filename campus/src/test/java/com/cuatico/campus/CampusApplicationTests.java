package com.cuatico.campus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cuatico.campus.entities.Group;
import com.cuatico.campus.entities.Teacher;
import com.cuatico.campus.entities.User.Roles;
import com.cuatico.campus.entities.User.Status;
import com.cuatico.campus.repositories.UserRepository;

import lombok.AllArgsConstructor;

@SpringBootTest
class CampusApplicationTests {
	@Autowired
	private UserRepository repo;

	@Test
	void insertarUser() {
		Teacher teacher = Teacher.builder().email("email@test.com").status(Status.ACTIVE).name("Diana").surnames("Henao").phone("666666666").passwordHash("ÑKJSDHFS6574DF65324.Aa").roles(Set.of(Roles.TEACHER)).build();

		Set<Group> grupos = new HashSet<>();
		Group grupo1 = Group.builder().name("Grupo 1").status(com.cuatico.campus.entities.Group.Status.ACTIVE).build();
		Group grupo2 = Group.builder().name("Grupo 2").status(com.cuatico.campus.entities.Group.Status.ACTIVE).build();
		grupos.add(grupo1);
		grupos.add(grupo2);
		teacher.setAssignedGroups(grupos);

		repo.save(teacher);
	}

}
