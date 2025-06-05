package com.cuatico.campus.repositories;

import org.springframework.data.repository.CrudRepository;

import com.cuatico.campus.entities.Teacher;

public interface TeacherRepository extends CrudRepository<Teacher, Long> {

}
