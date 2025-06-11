package com.cuatico.campus.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Entity
@Data
@Table(name = "groups")
@NamedEntityGraph(name = "Group.withTeachers", attributeNodes = @NamedAttributeNode("groupTeachers"))
@NamedEntityGraph(name = "Group.withEnrollments", attributeNodes = @NamedAttributeNode("groupEnrollments"))
public class Group {

	public enum Status {
		ACTIVE, INACTIVE, SUSPENDED
	}

	public enum Type {
		SYNC, ASYNC
	}

//	----------ID-------------
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	Long id;

//	----------STATUS-------------
	@Enumerated(EnumType.STRING)
	Status status;

//	----------NOMBRE-------------
	@EqualsAndHashCode.Include
	String name;

//	----------FECHAS Y HORARIO-------------
	LocalDateTime startDate;
	LocalDateTime endDate;
	String horario;

//	----------SALAS DE DIRECTO-------------
	String slackURL;

//	----------ID-------------
	@ManyToMany
	@JoinTable(name = "group_teachers", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "teacher_id"))
	@Builder.Default
	Set<Teacher> groupTeachers = new HashSet<>();

//	----------ESTUDIANTES MÁXIMOS-------------
	Integer maxStudents;

//	----------ESTUDIANTES ACT/INCT-------------
	Integer activeEnrolled;
	Integer inactiveEnrolled;

//	----------LISTA DE MATRICULADOS-------------
	@OneToMany
	@JoinTable(name = "group_enrollments", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "enrollment_id"))
	@Builder.Default
	Set<Enrollment> groupEnrollments = new HashSet<>();

//	Set<Task> tasks; (para cuando se implemente la entidad módulo)
//	Course course;   (para cuando se implemente)

	
//	----------AÑADIR MATRÍCULA AL GRUPO-------------

	public void addEnrollment(Enrollment enrollment) {
		if (enrollment != null) {
			this.groupEnrollments.add(enrollment);
			enrollment.setGroup(this);
		}
	}

//	----------ELIMINAR MATRÍCULA DEL GRUPO------------- IMPORTANTE! INTENTAR NO UTILIZAR PORQUE ELIMINA LA TRAZA
//	  										            DE LA MATRICULA. MEJOR UTILIZAR updateEnrollmentStatus

	public void removeEnrollment(Enrollment enrollment) {
		if (enrollment != null) {
			this.groupEnrollments.remove(enrollment);
			enrollment.setStudent(null);
		}
	}

//----------CAMBIAR EL STATUS DE LA  MATRÍCULA-------- DEJA LA MATRÍCULA INHABILITADA PERO CONSERVA SU TRAZA EN DB

	public boolean updateEnrollmentStatus(Long enrollmentId, Enrollment.Status newStatus) {
		for (Enrollment enrollment : this.groupEnrollments) {
			if (enrollment.getId().equals(enrollmentId)) {
				enrollment.setStatus(newStatus);
				return true;
			}
		} return false;
	}
}