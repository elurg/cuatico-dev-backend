package com.cuatico.campus.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@DiscriminatorValue("TEACHER")
@NamedEntityGraph(name = "Teacher.withGroups", attributeNodes = @NamedAttributeNode("teacherGroups"))
public class Teacher extends User {

//	----------GRUPOS ASIGNADOS-------------

	@ManyToMany(mappedBy = "groupTeachers")
	@Builder.Default
	@JsonIgnore
	private List<Group> teacherGroups = new ArrayList<>();

}