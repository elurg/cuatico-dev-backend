package com.cuatico.campus.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import com.cuatico.campus.entities.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
	
	@EntityGraph(attributePaths = {"Group.withStaff", "Group.withEnrollments", "Group.withModules"})
	@NonNull Optional<Group> findByName(@NonNull String name);

	@EntityGraph(attributePaths = {"Group.withStaff", "Group.withEnrollments", "Group.withModules"})
	@NonNull Optional<Group> findById(@NonNull Long id);
	
//	@EntityGraph(value = "Group.withEnrollments")
//	Group findByNameAndGroupEnrollments(String name);
//	SI EL METODO CON VARIOS GRAFOS FUNCIONA, NO SE NECESITA ESTE

}