package com.cuatico.campus.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cuatico.campus.entities.Student;
import com.cuatico.campus.repositories.StudentRepository;
import com.cuatico.campus.services.StudentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class StudentController {

	private final StudentRepository repo;
    private final StudentService studentService;

	@PostMapping("/register")
	public ResponseEntity<Student> registerStudent(@RequestBody @Valid Student student) {
		if (repo.findByEmail(student.getEmail()) != null) {
			return ResponseEntity.badRequest().build();
		}
		Student savedStudent = repo.save(student);
		return ResponseEntity.ok(savedStudent);
	}

	@GetMapping("{id}")
	public Student getStudent(@PathVariable Long id) {
		return studentService.findById(id);
		
	}

	@GetMapping("")
	public List<Student> findStudents() {
		return  studentService.findAll();
	}

}
