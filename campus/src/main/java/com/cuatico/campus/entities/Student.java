package com.cuatico.campus.entities;

import java.util.HashSet;
import java.util.Set;

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
	Set<Enrollment> studentEnrollments = new HashSet<>();

//	----------AÑADIR MATRÍCULA-------------

	public void addEnrollment(Enrollment enrollment) {
		if (enrollment != null) {
			this.studentEnrollments.add(enrollment);
			enrollment.setStudent(this);
		}
	}

//	----------ELIMINAR MATRÍCULA------------- IMPORTANTE! INTENTAR NO UTILIZAR PORQUE ELIMINA LA TRAZA
//											  DE LA MATRICULA. MEJOR UTILIZAR updateEnrollmentStatus

	public void removeEnrollment(Enrollment enrollment) {
		if (enrollment != null) {
			this.studentEnrollments.remove(enrollment);
			enrollment.setStudent(null);
		}
	}

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
