package com.cuatico.campus.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cuatico.campus.entities.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
	
	@EntityGraph(value = "Group.withTeachers")
	Group findByName(String name);

	@EntityGraph(value = "Group.withTeachers")
	Optional<Group> findById(Long id);
	
//	@EntityGraph(value = "Group.withEnrollments")
//	Group findByNameAndGroupEnrollments(String name);

}
