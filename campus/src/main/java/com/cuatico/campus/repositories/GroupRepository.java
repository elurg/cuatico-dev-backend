package com.cuatico.campus.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import com.cuatico.campus.entities.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
	
	@EntityGraph(attributePaths = {"Group.withStaff", "Group.withEnrollments", "Group.withSections"})
	@NonNull Optional<Group> findByName(@NonNull String name);

	@EntityGraph("Group.withAll")
//	@Query("from Group g left join fetch g.groupStaff where g.id=:id")
	@NonNull Optional<Group> findById(@NonNull Long id);
}