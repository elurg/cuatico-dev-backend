package com.cuatico.campus.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cuatico.campus.entities.Student;
import com.cuatico.campus.repositories.StudentRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {
	
	private final StudentRepository repo;
	
	@PostMapping("/register")
	public ResponseEntity<Student> registerStudent(@RequestBody @Valid Student student){
		if(repo.findByEmail(student.getEmail()) != null) {
	        return ResponseEntity.badRequest().build();
	    }
		Student savedStudent = repo.save(student);
		return ResponseEntity.ok(savedStudent);
	}

}
