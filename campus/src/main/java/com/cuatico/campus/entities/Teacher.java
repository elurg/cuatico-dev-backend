package com.cuatico.campus.entities;

import java.util.Set;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import lombok.AllArgsConstructor;
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
@NamedEntityGraph(name = "Teacher.withGroups", attributeNodes = @NamedAttributeNode("assignedGroups"))
public class Teacher extends User {
	
	@ManyToMany(mappedBy = "assignedTeachers")
	Set<Group> assignedGroups;
}