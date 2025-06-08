package com.cuatico.campus.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import com.cuatico.campus.entities.Enrollment;
import com.cuatico.campus.entities.Group;
import com.cuatico.campus.entities.Student;
import com.cuatico.campus.entities.Teacher;

public interface EnrollmentRepository extends CrudRepository<Enrollment, Long> {
	
	@EntityGraph(value = "Enrollment.withStudentAndGroup")
    Set<Enrollment> findByStudent(Student student);
    
    @EntityGraph(value = "Enrollment.withStudentAndGroup")
    Set<Enrollment> findByGroup(Group group);
    
    @EntityGraph(value = "Enrollment.withStudentAndGroup")
    Set<Enrollment> findByTeacher(Teacher teacher);

}
