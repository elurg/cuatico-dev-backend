package com.cuatico.campus.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.lang.NonNull;

import com.cuatico.campus.entities.Teacher;

public interface TeacherRepository extends UserBaseRepository<Teacher> {
	
	@EntityGraph(value = "Teacher.withGroups")
	@NonNull Optional<Teacher> findByEmail(@NonNull String email);
	

	@EntityGraph(value = "Teacher.withGroups")
	@NonNull Optional<Teacher> findById(@NonNull Long id);
}
