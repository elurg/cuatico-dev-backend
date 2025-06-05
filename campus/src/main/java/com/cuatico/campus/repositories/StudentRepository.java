package com.cuatico.campus.repositories;

import org.springframework.data.repository.CrudRepository;

import com.cuatico.campus.entities.Student;

public interface StudentRepository extends CrudRepository<Student, Long>{

}
