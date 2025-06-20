package com.cuatico.campus.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("TEACHER")
@NamedEntityGraph(name = "Teacher.withGroups", attributeNodes = @NamedAttributeNode("groups"))
public class Teacher extends Staff {

}