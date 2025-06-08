package com.cuatico.campus.repositories;

import org.springframework.data.jpa.repository.EntityGraph;

import com.cuatico.campus.entities.Teacher;

public interface TeacherRepository extends UserBaseRepository<Teacher> {
	
	@EntityGraph(value = "Teacher.withGroups")
	Teacher findByEmail(String email);
}
