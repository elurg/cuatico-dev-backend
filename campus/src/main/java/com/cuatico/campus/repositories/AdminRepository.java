package com.cuatico.campus.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.lang.NonNull;

import com.cuatico.campus.entities.Admin;

public interface AdminRepository extends UserBaseRepository<Admin> {
	
	@EntityGraph(value = "Admin.withGroups")
	@NonNull Optional<Admin> findByEmail(@NonNull String email);
	

	@EntityGraph(value = "Admin.withGroups")
	@NonNull Optional<Admin> findById(@NonNull Long id);
}
