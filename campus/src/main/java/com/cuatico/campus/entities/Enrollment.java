package com.cuatico.campus.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name="enrollments")
@NamedEntityGraph(name = "Enrollment.withStudentAndGroup", attributeNodes = {
												@NamedAttributeNode("student"),
												@NamedAttributeNode("group")})
public class Enrollment {
	
	public enum Status {
	    ACTIVE, INACTIVE, SUSPENDED
	}
	
	
//	----------ID-------------
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	
//	----------STATUS-------------
	@Enumerated(EnumType.STRING)
	Status status;
	
	
//	----------FECHAS-------------
	LocalDateTime enrollmentDate;
	LocalDateTime endDate;
	
	
//	----------ESTUDIANTE-------------
	@NotNull
	@ManyToOne
	Student student;
	
	
//	----------GRUPO-------------	
	@NotNull
	@ManyToOne
	Group group;
	
	
//	----------NOTA-------------
	Integer finalGrade;
	Boolean hasPassed;
	
	
//	Course course; (para cuando esté implementado)
}
