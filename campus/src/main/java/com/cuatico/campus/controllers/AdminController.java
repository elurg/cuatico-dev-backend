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

import com.cuatico.campus.entities.Admin;
import com.cuatico.campus.entities.Group;
import com.cuatico.campus.services.AdminService;
import com.cuatico.campus.services.ServiceException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminController {
	private final AdminService adminService;

	@PostMapping("/register")
	public Admin registerAdmin(@RequestBody @Valid Admin admin) {
		Admin savedAdmin = adminService.registerAdmin(admin);
		if (savedAdmin == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		return savedAdmin;
	}

	@GetMapping("")
	public List<Admin> findAdmins() {
		return adminService.findAll();
	}

	@GetMapping("{id}")
	public Admin findAdminById(@PathVariable Long id) {
		Admin checkedAdmin = adminService.findById(id);
		if (checkedAdmin == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return checkedAdmin;
	}

	@PutMapping("/{id}")
	public Admin updateAdmin(@PathVariable Long id, @RequestBody @Valid Admin admin) {
		Admin updatedAdmin = adminService.updateAdmin(id, admin);
		if (updatedAdmin == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		return updatedAdmin;

	}

	@PatchMapping("/{id}/deactivate")
	public void deactivateAmin(@PathVariable Long id) {
		Admin deactivatedAdmin = adminService.findById(id);
		if (deactivatedAdmin == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		adminService.deactivateAdmin(id);

	}

	@GetMapping("/{id}/groups")
	public List<Group> getAdminGroups(@PathVariable Long id) {
		List <Group> adminGroups = adminService.getAdminGroups(id);
		if (adminGroups == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return adminGroups;
	}

	@PatchMapping("/{id}/groups/{groupId}/add")
	public void addGroupToAdmin(@PathVariable Long id, @PathVariable Long groupId) {
	    try {
	        adminService.addGroupToAdmin(id, groupId);
	    } catch (ServiceException e) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se pudo agregar el grupo al admin", e);
	    }
	}

	@PatchMapping("/{id}/groups/{groupId}/remove")
	public void removeGroupFromAdmin(@PathVariable Long id, @PathVariable Long groupId) {
		try {
	        adminService.removeGroupFromAdmin(id, groupId);
	    } catch (ServiceException e) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se pudo eliminar el grupo al admin", e);
	    }
	}
}
