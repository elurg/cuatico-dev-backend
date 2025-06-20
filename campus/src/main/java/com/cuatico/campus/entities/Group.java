package com.cuatico.campus.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@NamedEntityGraph(name = "Group.withStaff", attributeNodes = @NamedAttributeNode("groupStaff"))
@NamedEntityGraph(name = "Group.withEnrollments", attributeNodes = @NamedAttributeNode("groupEnrollments"))
public class Group {

	public enum Status {
		ACTIVE, INACTIVE, SUSPENDED
	}

	public enum Type {
		PRESENTIAL, REMOTE, MIXED
	}

	// ----------ID-------------
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	// ----------STATUS-------------
	@Enumerated(EnumType.STRING)
	private Status status;

	// ----------TYPE-------------
	@Enumerated(EnumType.STRING)
	private Type type;

	// ----------CURSO-------------
	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;

	// ----------NOMBRE-------------
	@EqualsAndHashCode.Include
	private String name;

	// ----------FECHAS Y HORARIO-------------
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private String timetable;

	// ----------SALAS DE DIRECTO-------------
	private String slackURL;

	// ----------LISTA DE STAFF (TEACHERS O ADMINS)-------------
	@ManyToMany
	@JoinTable(name = "group_staff", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "staff_id"))
	@Builder.Default
	@JsonIgnore
	private List<Staff> groupStaff = new ArrayList<>();

	// ----------ESTUDIANTES MÁXIMOS-------------
	private Integer maxStudents;

	// ----------ESTUDIANTES ACT/INCT-------------
	private Integer activeEnrolled;
	private Integer inactiveEnrolled;

	// ----------LISTA DE MATRICULADOS-------------
	@OneToMany
	@JoinTable(name = "group_enrollments", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "enrollment_id"))
	@Builder.Default
	@JsonIgnore
	private List<Enrollment> groupEnrollments = new ArrayList<>();

	// private List<Task> tasks; (para cuando se implemente la entidad módulo)

	//// -----------------------------------------------------------------------------------
	//// ------------------------MÉTODOS DE LA
	//// CLASE----------------------------------------
	//// ------------------------(MIGRAR A SERVICE
	//// DRIVEN)----------------------------------
	//
	//// ----------AÑADIR MATRÍCULA AL GRUPO-------------
	//
	// public void addEnrollment(Enrollment enrollment) {
	// if (enrollment == null)
	// return;
	// this.groupEnrollments.add(enrollment);
	// enrollment.setGroup(this);
	// Student student = enrollment.getStudent();
	// if (student != null) {
	// student.getStudentEnrollments().add(enrollment);
	// }
	// }
	//
	//// ----------ELIMINAR MATRÍCULA DEL GRUPO------------- IMPORTANTE! INTENTAR NO
	//// UTILIZAR PORQUE ELIMINA LA TRAZA
	//// DE LA MATRICULA. MEJOR UTILIZAR updateEnrollmentStatus
	//
	// public void removeEnrollment(Enrollment enrollment) {
	// if (enrollment == null)
	// return;
	// this.groupEnrollments.remove(enrollment);
	// Student student = enrollment.getStudent();
	// if (student != null) {
	// student.getStudentEnrollments().remove(enrollment);
	// }
	// enrollment.setGroup(null);
	// }
	//
	//// ----------CAMBIAR EL STATUS DE LA MATRÍCULA-------- DEJA LA MATRÍCULA
	//// INHABILITADA PERO CONSERVA SU TRAZA EN DB
	//
	// public boolean updateEnrollmentStatus(Long enrollmentId, Enrollment.Status
	//// newStatus) {
	// for (Enrollment enrollment : this.groupEnrollments) {
	// if (enrollment.getId().equals(enrollmentId)) {
	// enrollment.setStatus(newStatus);
	// return true;
	// }
	// }
	// return false;
	// }
	//
	//// ----------AÑADIR TEACHER AL GRUPO-------------
	//
	// public void addTeacher(Teacher teacher) {
	// if (teacher != null && this.groupTeachers.add(teacher)) {
	// if (!teacher.getTeacherGroups().contains(this)) {
	// teacher.addGroup(this);
	// }
	// }
	// }
	//
	//// ----------ELIMINAR TEACHER DEL GRUPO-------------
	//
	// public void removeTeacher(Teacher teacher) {
	// if (teacher != null && this.groupTeachers.remove(teacher)) {
	// if (teacher.getTeacherGroups().contains(this)) {
	// teacher.removeGroup(this);
	// }
	// }
	// }

}