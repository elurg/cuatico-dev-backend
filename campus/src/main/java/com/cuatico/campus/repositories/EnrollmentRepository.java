package com.cuatico.campus.repositories;

import org.springframework.data.repository.CrudRepository;

import com.cuatico.campus.entities.Enrollment;

public interface EnrollmentRepository extends CrudRepository<Enrollment, Long> {

}
