package com.cuatico.campus.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cuatico.campus.entities.Group;
import com.cuatico.campus.entities.Staff;
import com.cuatico.campus.services.ServiceException;
import com.cuatico.campus.services.StaffService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StaffController {
	private final StaffService staffService;


	@GetMapping("")
	public List<Staff> findStaff() {
		return staffService.findAll();
	}

	@GetMapping("{id}")
	public Staff findStaffById(@PathVariable Long id) {
		Staff checkedStaff = staffService.findById(id);
		if (checkedStaff == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return checkedStaff;
	}

	@GetMapping("/{id}/groups")
	public List<Group> getStaffGroups(@PathVariable Long id) {
		List <Group> staffGroups = staffService.getStaffGroups(id);
		if (staffGroups == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return staffGroups;
	}

	@PatchMapping("/{id}/groups/{groupId}/add")
	public void addGroupToStaff(@PathVariable Long id, @PathVariable Long groupId) {
	    try {
	        staffService.addGroupToStaff(id, groupId);
	    } catch (ServiceException e) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se pudo agregar el grupo al staff", e);
	    }
	}

	@PatchMapping("/{id}/groups/{groupId}/remove")
	public void removeGroupFromStaff(@PathVariable Long id, @PathVariable Long groupId) {
		try {
	        staffService.removeGroupFromStaff(id, groupId);
	    } catch (ServiceException e) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se pudo eliminar el grupo al staff", e);
	    }
	}
}
