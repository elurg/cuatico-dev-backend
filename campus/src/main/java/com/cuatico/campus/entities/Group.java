package com.cuatico.campus.entities;


import java.time.LocalDateTime;
import java.util.Set;
import jakarta.persistence.*;
import lombok.*;

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
	@ManyToMany
	@JoinTable(
		    name = "teacher_group",
		    joinColumns = @JoinColumn(name = "group_id"),
		    inverseJoinColumns = @JoinColumn(name = "teacher_id")
		)
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