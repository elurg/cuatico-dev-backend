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
	public Teacher registerTeacher(@RequestBody @Valid Teacher teacher) {
		Teacher savedTeacher = teacherService.registerTeacher(teacher);
		if (savedTeacher == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		return savedTeacher;
	}

	@GetMapping("")
	public List<Teacher> findTeachers() {
		return teacherService.findAll();
	}

	@GetMapping("{id}")
	public Teacher getTeacher(@PathVariable Long id) {
		Teacher checkedTeacher = teacherService.findById(id);
		if (checkedTeacher == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return checkedTeacher;
	}

	@PutMapping("/{id}")
	public Teacher updateTeacher(@PathVariable Long id, @RequestBody @Valid Teacher teacher) {
		Teacher updatedTeacher = teacherService.updateTeacher(id, teacher);
		if (updatedTeacher == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		return updatedTeacher;

	}

	@PatchMapping("/{id}/deactivate")
	public void deactivateTeacher(@PathVariable Long id) {
		Teacher deactivatedTeacher = teacherService.findById(id);
		if (deactivatedTeacher == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		teacherService.deactivateTeacher(id);

	}

	@GetMapping("/{id}/groups")
	public List<Group> getTeacherGroups(@PathVariable Long id) {
		List <Group> teacherGroups = teacherService.getTeacherGroups(id);
		if (teacherGroups == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return teacherGroups;
	}

	@PatchMapping("/{id}/groups/{groupId}/add")
	public void addGroupToTeacher(@PathVariable Long id, @PathVariable Long groupId) {
	    try {
	        teacherService.addGroupToTeacher(id, groupId);
	    } catch (ServiceException e) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se pudo agregar el grupo al profesor", e);
	    }
	}

	@PatchMapping("/{id}/groups/{groupId}/remove")
	public void removeGroupFromTeacher(@PathVariable Long id, @PathVariable Long groupId) {
		try {
	        teacherService.removeGroupFromTeacher(id, groupId);
	    } catch (ServiceException e) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se pudo eliminar el grupo al profesor", e);
	    }
	}
}
