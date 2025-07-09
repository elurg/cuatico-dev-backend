package com.cuatico.campus.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import com.cuatico.campus.entities.Course;

public interface CourseRepository extends JpaRepository<Course, Long>{
	
	@EntityGraph(attributePaths = "groups")
	@NonNull Optional<Course> findByTitle(@NonNull String name);

	@EntityGraph(value = "groups")
	@NonNull Optional<Course> findById(@NonNull Long id);

}
