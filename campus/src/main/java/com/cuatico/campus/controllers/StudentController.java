package com.cuatico.campus.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cuatico.campus.entities.Enrollment;
import com.cuatico.campus.entities.Student;
import com.cuatico.campus.services.StudentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StudentController {

	private final StudentService studentService;

	@PostMapping("/register")
	public Student registerStudent(@RequestBody @Valid Student student) {
		Student savedStudent = studentService.registerStudent(student);
		if(savedStudent == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		return savedStudent;
	}

	@GetMapping("")
	public List<Student> findStudents() {
		return studentService.findAll();
	}

	@GetMapping("{id}")
	public Student getStudent(@PathVariable Long id) {
		Student checkedStudent = studentService.findById(id);
		if(checkedStudent == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return checkedStudent;

	}

	@GetMapping("/{id}/enrollments")
	public List<Enrollment> getStudentEnrollments(@PathVariable Long id) {
		List<Enrollment> checkedEnrollments = studentService.getStudentEnrollments(id);
		if(checkedEnrollments == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return checkedEnrollments;
		
	}

	@PutMapping("/{id}")
	public Student updateStudent(@PathVariable Long id, @RequestBody @Valid Student student) {
		Student updatedStudent = studentService.updateStudent(id, student);
		if(updatedStudent == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		return updatedStudent;
		
	}

	@PatchMapping("/{id}/deactivate")
	public void deactivateStudent(@PathVariable Long id) {
		Student deactivatedStudent = studentService.findById(id);
		if(deactivatedStudent == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		studentService.deactivateStudent(id);
	}

}
