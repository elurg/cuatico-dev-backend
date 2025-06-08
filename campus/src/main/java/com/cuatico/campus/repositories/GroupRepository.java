package com.cuatico.campus.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import com.cuatico.campus.entities.Group;

public interface GroupRepository extends CrudRepository<Group, Long> {
	
	@EntityGraph(value = "Group.withTeachers")
	Group findByName(String name);

}
