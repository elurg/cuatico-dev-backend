package com.cuatico.campus.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cuatico.campus.entities.Enrollment;
import com.cuatico.campus.entities.Student;
import com.cuatico.campus.services.ServiceException;
import com.cuatico.campus.services.StudentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class StudentController {

	private final StudentService studentService;

	@PostMapping("/register")
	public ResponseEntity<Student> registerStudent(@RequestBody @Valid Student student) {
		try {
			Student savedStudent = studentService.registerStudent(student);
			return ResponseEntity.ok(savedStudent);
		} catch (ServiceException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("")
	public List<Student> findStudents() {
		return studentService.findAll();
	}

	@GetMapping("{id}")
	public ResponseEntity<Student> getStudent(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(studentService.findById(id));
		} catch (ServiceException e) {
			return ResponseEntity.notFound().build();
		}

	}

	@GetMapping("/{id}/enrollments")
	public ResponseEntity<List<Enrollment>> getStudentEnrollments(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(studentService.getStudentEnrollments(id));
		} catch (ServiceException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody @Valid Student student) {
		try {
			Student updated = studentService.updateStudent(id, student);
			return ResponseEntity.ok(updated);
		} catch (ServiceException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PatchMapping("/{id}/deactivate")
	public ResponseEntity<Void> deactivateStudent(@PathVariable Long id) {
		try {
			studentService.deactivateStudent(id);
			return ResponseEntity.ok().build();
		} catch (ServiceException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
