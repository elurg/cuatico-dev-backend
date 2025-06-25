package com.cuatico.campus.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedEntityGraph(name = "Section.withResources", attributeNodes = @NamedAttributeNode("resources"))
@Entity
@Table(name = "sections")
public class Section {

	public enum Status {
		ACTIVE, INACTIVE, HIDDEN
	}

//	----------ID-------------	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

//	----------STATUS-------------
	@Enumerated(EnumType.STRING)
	@Builder.Default
	private Status status = Section.Status.ACTIVE;

//	----------TÍTULO-------------
	@NotNull
	private String title;

//	----------DESCRIPCIÓN-------------
	private String description;

//	----------POSICIÓN-------------
	private Integer position;

//	----------GRUPO-------------
	@ManyToOne(optional = false)
	private Group group;

//	----------LISTA DE MÓDULOS-------------
	@OneToMany(mappedBy = "section")
	@Builder.Default
	private List<Resource> resources = new ArrayList<>();
}