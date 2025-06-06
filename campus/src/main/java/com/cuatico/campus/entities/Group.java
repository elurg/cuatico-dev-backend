package com.cuatico.campus.entities;


import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
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