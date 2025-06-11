package com.cuatico.campus.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cuatico.campus.entities.Enrollment;
import com.cuatico.campus.entities.Group;
import com.cuatico.campus.entities.Student;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
	
	@EntityGraph(value = "Enrollment.withStudentAndGroup")
    Set<Enrollment> findByStudent(Student student);
    
    @EntityGraph(value = "Enrollment.withStudentAndGroup")
    Set<Enrollment> findByGroup(Group group);

}
