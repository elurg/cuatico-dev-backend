package com.cuatico.campus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cuatico.campus.entities.Resource;

public interface ResourceRepository extends JpaRepository<Resource, Long>{

}
