package com.cuatico.campus.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import com.cuatico.campus.entities.Module;

public interface ModuleRepository extends JpaRepository<Module, Long>{

	@EntityGraph(value = "Module.withResources")
    @NonNull Optional<Module> findById(@NonNull Long id);

    @EntityGraph(value = "Module.withResources")
    @NonNull Optional<Module> findByTitle(@NonNull String title);
}
