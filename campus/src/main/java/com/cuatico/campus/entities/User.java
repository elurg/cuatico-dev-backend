package com.cuatico.campus.entities;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Entity
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
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	Status status;
	@NotBlank
	@Size(max = 30)
	String name;
	@NotBlank
	@Size(max = 50)
	String surnames;
	@Size(max = 20)
	String username;
	@Column(unique = true, nullable = false)
	@Size(max = 50)
	String email;
	@NotBlank
	@Size(max = 200)
	String passwordHash;
	@Size(max = 30)
	String phone;
	@ElementCollection
	Set<Roles> roles;
	
}