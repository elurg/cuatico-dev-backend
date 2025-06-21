package com.cuatico.campus.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.lang.NonNull;

import com.cuatico.campus.entities.Staff;

public interface StaffRepository extends UserBaseRepository<Staff> {
	
	@EntityGraph(value = "Staff.withGroups")
	@NonNull Optional<Staff> findByEmail(@NonNull String email);
	

	@EntityGraph(value = "Staff.withGroups")
	@NonNull Optional<Staff> findById(@NonNull Long id);
}