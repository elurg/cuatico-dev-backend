package com.cuatico.campus.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type")
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(name = "uk_user_email", columnNames = "email") }, indexes = {
	    @Index(name = "idx_user_email", columnList = "email"),
	    @Index(name = "idx_user_passwordHash", columnList = "passwordHash"),
	    @Index(name = "idx_user_status", columnList = "status")})
public abstract class User {
	
	public enum Status {
	    ACTIVE, INACTIVE, SUSPENDED
	}
	
	public enum Roles {
	    ADMIN, TEACHER, STUDENT
	}
	
	
//	----------ID-------------
	@Id
	@PositiveOrZero
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	Long id;
	

//	----------STATUS-------------
	@Enumerated(EnumType.STRING)
	Status status;
	
	

//	----------NAME-------------
	@NotBlank
	@Size(max = 30)
	String name;
	
	
	
//	----------SURNAME-------------
	@NotBlank
	@Size(max = 50)
	String surnames;
	
	
//	----------USERNAME-------------
	@Size(max = 20)
	String username;
	
	
//	----------EMAIL-------------
	@Column(unique = true, nullable = false)
	@Size(max = 50)
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Formato de correo electrónico inválido")
	String email;
	
	
//	----------PASSWORDHASH-------------
	@NotBlank
	@Size(max = 200)
	String passwordHash;
	
	
//	----------PHONE-------------        (es un string por si alguien pone algo así: "+34 600-00-00-00")
	@Size(max = 30)
	String phone;

	
	
//	----------ROLES------------- Plantear si quitar roles porque puede ser que con el discriminatorycolumn sea suficiente
//								 La idea es que cada tipo de usuario solo pueda ser instanciado una vez con un email único
//								 Si un alumno de repente pasa a ser teacher, deberá crear una cuenta de mail nueva
//								 que además puede ayudar a corporativizar la empresa (newTeacher.cuatico@gmail.com)

	@Enumerated(EnumType.STRING)
	@Builder.Default
	Set<Roles> roles = new HashSet<>();
	
}