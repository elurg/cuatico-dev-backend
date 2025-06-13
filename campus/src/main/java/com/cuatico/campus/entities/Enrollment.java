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
	private Long id;
	
	
//	----------STATUS-------------
	@NotNull
	@Enumerated(EnumType.STRING)
	@Builder.Default
	private Status status = Status.ACTIVE;
	
	
//	----------FECHAS-------------
	@NotNull
	@Builder.Default
	private LocalDateTime enrollmentDate = LocalDateTime.now();
	
	private LocalDateTime endDate;
	
	
//	----------ESTUDIANTE-------------
	@NotNull
	@ManyToOne
	private Student student;
	
	
//	----------GRUPO-------------	
	@NotNull
	@ManyToOne
	private Group group;
	
	
//	----------NOTA-------------
	private Integer finalGrade;
	private Boolean hasPassed;
	
	
//	Course course; (para cuando esté implementado)
}
