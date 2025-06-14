package com.cuatico.campus.services;

import java.util.List;

import com.cuatico.campus.entities.Enrollment;
import com.cuatico.campus.entities.Group;
import com.cuatico.campus.entities.Student;

public interface StudentService {
	Student registerStudent(Student student);
    Enrollment enrollStudentInGroup(Student student, Group group);
    void removeStudentEnrollment(Student student, Enrollment enrollment);
    List<Student> findAll();
    Student findById(Long id);
}
