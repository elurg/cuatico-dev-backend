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
public class Group {
	
	public enum Status {
	    ACTIVE, INACTIVE, SUSPENDED
	}
	
	public enum Type {
		SYNC, ASYNC
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@Enumerated(EnumType.STRING)
	Status status;
	String name;
	LocalDateTime startDate;
	LocalDateTime endDate;	
	String horario;
	String slackURL;
	@ManyToMany
	Set<Teacher> teachers;
	Integer maxStudents;
	Integer activeEnrolled;
	Integer inactiveEnrolled;
	@ManyToMany
	Set<Student> enrolled;
//	Set<Task> tasks; (para cuando se implemente la entidad módulo)
//	Course course;   (para cuando se implemente)
}