package com.cuatico.campus.entities;


import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	String name;
	
	
//	----------FECHAS Y HORARIO-------------
	LocalDateTime startDate;
	LocalDateTime endDate;	
	String horario;
	
	
//	----------SALAS DE DIRECTO-------------
	String slackURL;
	
	
//	----------ID-------------
	@ManyToMany(mappedBy = "teacherGroups")
	Set<Teacher> groupTeachers;
	
	
//	----------ESTUDIANTES MÁXIMOS-------------
	Integer maxStudents;
	
	
//	----------ESTUDIANTES ACT/INCT-------------
	Integer activeEnrolled;
	Integer inactiveEnrolled;
	
	
//	----------LISTA DE MATRICULADOS-------------
	@OneToMany(mappedBy = "group")
	Set<Enrollment> groupEnrollments;
	
	
//	Set<Task> tasks; (para cuando se implemente la entidad módulo)
//	Course course;   (para cuando se implemente)
}