package com.cuatico.campus.repositories;

import org.springframework.data.repository.CrudRepository;

import com.cuatico.campus.entities.Group;

public interface GroupRepository extends CrudRepository<Group, Long> {
	
	Group findByName(String name);

}
