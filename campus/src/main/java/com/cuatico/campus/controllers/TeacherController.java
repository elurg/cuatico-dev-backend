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

import com.cuatico.campus.entities.Group;
import com.cuatico.campus.entities.Teacher;
import com.cuatico.campus.services.ServiceException;
import com.cuatico.campus.services.TeacherService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TeacherController {
	private final TeacherService teacherService;

	@PostMapping("/register")
	public ResponseEntity<Teacher> registerTeacher(@RequestBody @Valid Teacher teacher) {
		try {
			Teacher savedTeacher = teacherService.registerTeacher(teacher);
			return ResponseEntity.ok(savedTeacher);
		} catch (ServiceException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("")
	public List<Teacher> findTeachers() {
		return teacherService.findAll();
	}

	@GetMapping("{id}")
	public ResponseEntity<Teacher> getTeacher(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(teacherService.findById(id));
		} catch (ServiceException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Teacher> updateTeacher(@PathVariable Long id, @RequestBody @Valid Teacher teacher) {
		try {
			Teacher updated = teacherService.updateTeacher(id, teacher);
			return ResponseEntity.ok(updated);
		} catch (ServiceException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PatchMapping("/{id}/deactivate")
	public ResponseEntity<Void> deactivateTeacher(@PathVariable Long id) {
		try {
			teacherService.deactivateTeacher(id);
			return ResponseEntity.ok().build();
		} catch (ServiceException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/{id}/groups")
	public ResponseEntity<List<Group>> getTeacherGroups(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(teacherService.getTeacherGroups(id));
		} catch (ServiceException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PatchMapping("/{id}/groups/{groupId}/add")
	public ResponseEntity<Void> addGroupToTeacher(@PathVariable Long id, @PathVariable Long groupId) {
		try {
			teacherService.addGroupToTeacher(id, groupId);
			return ResponseEntity.ok().build();
		} catch (ServiceException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PatchMapping("/{id}/groups/{groupId}/remove")
	public ResponseEntity<Void> removeGroupFromTeacher(@PathVariable Long id, @PathVariable Long groupId) {
		try {
			teacherService.removeGroupFromTeacher(id, groupId);
			return ResponseEntity.ok().build();
		} catch (ServiceException e) {
			return ResponseEntity.badRequest().build();
		}
	}
}
