package com.cuatico.campus.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import com.cuatico.campus.entities.Section;

public interface SectionRepository extends JpaRepository<Section, Long>{

	@EntityGraph(value = "Section.withResources")
    @NonNull Optional<Section> findById(@NonNull Long id);

    @EntityGraph(value = "Section.withResources")
    @NonNull Optional<Section> findByTitle(@NonNull String title);
}
