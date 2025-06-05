package com.cuatico.campus.entities;


import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	LocalDateTime fechaInicio;
	LocalDateTime fechaFin;	
	Set<Teacher> docentes;
	Integer maxAlumnos;
	Integer inscritosActivos;
	Integer inscritosNoActivos;
	Set<Student> matriculados;
//	Set<Task> tareas; (para cuando se implemente la entidad módulo)
}