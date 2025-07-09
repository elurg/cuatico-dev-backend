package com.cuatico.campus.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import com.cuatico.campus.entities.Enrollment;
import com.cuatico.campus.entities.Group;
import com.cuatico.campus.entities.Student;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
	
	@EntityGraph(value = "Enrollment.withAll")
    @NonNull List<Enrollment> findByStudent(@NonNull Student student);
    
    @EntityGraph(value = "Enrollment.withAll")
    @NonNull List<Enrollment> findByGroup(@NonNull Group group);

}
