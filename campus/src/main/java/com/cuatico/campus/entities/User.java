package com.cuatico.campus.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@JsonIgnoreProperties(value = {"password"}, allowSetters = true)
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
	
	public static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
	
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
	private Long id;
	

//	----------STATUS-------------
	@Enumerated(EnumType.STRING)
	@Builder.Default
	private Status status = User.Status.ACTIVE;
	
	

//	----------NAME-------------
	@NotBlank
	@Size(max = 30)
	private String name;
	
	
	
//	----------SURNAME-------------
	@NotBlank
	@Size(max = 50)
	private String surnames;
	
	
//	----------USERNAME-------------
	@Size(max = 20)
	private String username;
	
	
//	----------EMAIL-------------
	@Column(unique = true, nullable = false)
	@Size(max = 50)
	@Pattern(regexp = EMAIL_REGEX, message = "Formato de correo electrónico inválido")
	private String email;
	
	
//	----------PASSWORDHASH-------------
	@NotBlank
	@Size(max = 200)
	private String passwordHash;
	
	
//	----------PHONE-------------        (es un string por si alguien pone algo así: "+34 600-00-00-00")
	@Size(max = 30)
	private String phone;

	
	public String getRole() {
        return this.getClass().getSimpleName();
    }
	
}