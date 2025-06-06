package com.cuatico.campus.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
public class Enrollment {
	
	public enum Status {
	    ACTIVE, INACTIVE, SUSPENDED
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@Enumerated(EnumType.STRING)
	Status status;
	LocalDateTime enrollmentDate;
	LocalDateTime endDate;
	@ManyToOne
	Student student;
	@ManyToOne
	Group group;
	Integer finalGrade;
	Boolean hasPassed;
//	Course course; (para cuando esté implementado)
}
