package com.cuatico.campus.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;

import com.cuatico.campus.entities.Teacher;

public interface TeacherRepository extends UserBaseRepository<Teacher> {
	
	@EntityGraph(value = "Teacher.withGroups")
	Teacher findByEmail(String email);
	

	@EntityGraph(value = "Teacher.withGroups")
	Optional<Teacher> findById(Long id);
	

	@EntityGraph(value = "Teacher.withGroups")
	Teacher findByGroupId(Long id);
}
