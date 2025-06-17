package com.cuatico.campus.services;

import java.util.List;

import com.cuatico.campus.entities.Enrollment;
import com.cuatico.campus.entities.Group;
import com.cuatico.campus.entities.Student;

public interface StudentService {
	Student registerStudent(Student student);
	Student updateStudent(Long id, Student updatedStudent);
	void deactivateStudent(Long id);
	
    Enrollment enrollStudentInGroup(Student student, Group group);
    List<Enrollment> getStudentEnrollments(Long studentId);
    void removeStudentEnrollment(Student student, Enrollment enrollment);
    
    List<Student> findAll();
    Student findById(Long id);
}
