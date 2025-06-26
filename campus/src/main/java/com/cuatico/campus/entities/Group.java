package com.cuatico.campus.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
@NamedEntityGraph(name = "Group.withAll", attributeNodes = 
	{@NamedAttributeNode("groupStaff"), @NamedAttributeNode("groupEnrollments"), @NamedAttributeNode("sections"), @NamedAttributeNode("course")})
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
	@JsonManagedReference
	private List<Staff> groupStaff = new ArrayList<>();

	// ----------ESTUDIANTES MÁXIMOS-------------
	private Integer maxStudents;

	// ----------ESTUDIANTES ACT/INCT-------------
	private Integer activeEnrolled;
	private Integer inactiveEnrolled;

	// ----------LISTA DE MATRICULADOS-------------
	@OneToMany(mappedBy = "group")
	@Builder.Default
	@JsonIgnore
	private List<Enrollment> groupEnrollments = new ArrayList<>();

	// ----------LISTA DE MÓDULOS-------------
	@OneToMany(mappedBy = "group")
	@Builder.Default
	@JsonIgnore
	private List<Section> sections = new ArrayList<>();
}