package com.cuatico.campus.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
	@Builder.Default
	@JsonIgnore
	private List<Enrollment> studentEnrollments = new ArrayList<>();

//	-----------------------------------------------------------------------------------
//	------------------------MÉTODOS DE LA CLASE----------------------------------------
//	-----------------------(migrando a servicio)---------------------------------------


//	----------CAMBIAR EL STATUS DE LA  MATRÍCULA-------- DEJA LA MATRÍCULA INHABILITADA PERO CONSERVA SU TRAZA EN DB

	public boolean updateEnrollmentStatus(Long enrollmentId, Enrollment.Status newStatus) {
		for (Enrollment enrollment : this.studentEnrollments) {
			if (enrollment.getId().equals(enrollmentId)) {
				enrollment.setStatus(newStatus);
				return true;
			}
		}
		return false;
	}

}
