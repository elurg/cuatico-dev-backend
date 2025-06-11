package com.cuatico.campus.entities;

import java.util.Set;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@DiscriminatorValue("STUDENT")
@NamedEntityGraph(name = "Student.withEnrollments", attributeNodes = @NamedAttributeNode("studentEnrollments"))
public class Student extends User {
	
	
//	----------MATRÍCULAS DEL ALUMNO-------------
	@OneToMany(mappedBy = "student")
	Set<Enrollment> studentEnrollments;
}
