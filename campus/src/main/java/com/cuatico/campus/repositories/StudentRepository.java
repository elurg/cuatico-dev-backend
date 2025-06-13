package com.cuatico.campus.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.lang.NonNull;

import com.cuatico.campus.entities.Student;

public interface StudentRepository extends UserBaseRepository<Student>{
	

	@EntityGraph(value = "Student.withEnrollments")
	Student findByEmail(String email);

	@EntityGraph(value = "Student.withEnrollments")
	@NonNull Optional<Student> findById(@NonNull Long id);

}
