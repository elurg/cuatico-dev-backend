package com.cuatico.campus.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name="courses")
@NamedEntityGraph(name = "Course.withGroups", attributeNodes = @NamedAttributeNode("groups")
	)
public class Course {

	@Id
	@PositiveOrZero
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(max = 30)
	private String title;
	
	@Size(min = 50, max = 2000)
	private String description;
	
	private BigDecimal price;
	
	@OneToMany(mappedBy = "course")
	@Builder.Default
    @JsonIgnore
    private List<Group> groups = new ArrayList<>();
	
}
