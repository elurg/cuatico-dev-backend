package com.cuatico.campus.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import com.cuatico.campus.entities.*;

public interface EnrollmentRepository extends CrudRepository<Enrollment, Long> {
	
	@EntityGraph(value = "Enrollment.withStudentAndGroup")
    Set<Enrollment> findByStudent(Student student);
    
    @EntityGraph(value = "Enrollment.withStudentAndGroup")
    Set<Enrollment> findByGroup(Group group);

}
