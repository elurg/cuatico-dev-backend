package com.cuatico.campus.controllers;

import com.cuatico.campus.entities.Enrollment;
import com.cuatico.campus.entities.Group;
import com.cuatico.campus.entities.Module;
import com.cuatico.campus.services.GroupService;
import com.cuatico.campus.services.ServiceException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class GroupController {

    private final GroupService groupService;

    // CRUD básico
    @PostMapping("")
    public Group createGroup(@RequestBody @Valid Group group) {
        return groupService.createGroup(group);
    }

    @GetMapping("")
    public List<Group> findAll() {
        return groupService.findAll();
    }

    @GetMapping("/{id}")
    public Group findById(@PathVariable Long id) {
        Group group = groupService.findById(id);
        if (group == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return group;
    }

    @PutMapping("/{id}")
    public Group updateGroup(@PathVariable Long id, @RequestBody @Valid Group group) {
        try {
            return groupService.updateGroup(id, group);
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
    }

    @PatchMapping("/{id}/status")
    public void changeStatus(@PathVariable Long id, @RequestParam Group.Status status) {
        groupService.changeStatus(id, status);
    }

    // Staff management
    @PatchMapping("/{id}/staff/{staffId}/add")
    public void addStaff(@PathVariable Long id, @PathVariable Long staffId) {
        groupService.addStaff(id, staffId);
    }

    @PatchMapping("/{id}/staff/{staffId}/remove")
    public void removeStaff(@PathVariable Long id, @PathVariable Long staffId) {
        groupService.removeStaff(id, staffId);
    }

    // Enrollment management
    @PostMapping("/{id}/enrollments")
    public void addEnrollment(@PathVariable Long id, @RequestBody @Valid Enrollment enrollment) {
        groupService.addEnrollment(id, enrollment);
    }

    @DeleteMapping("/{id}/enrollments/{enrollmentId}")
    public void removeEnrollment(@PathVariable Long id, @PathVariable Long enrollmentId) {
        groupService.removeEnrollment(id, enrollmentId);
    }

    @PatchMapping("/{id}/enrollments/{enrollmentId}/status")
    public void updateEnrollmentStatus(@PathVariable Long id, @PathVariable Long enrollmentId, @RequestParam Enrollment.Status status) {
        groupService.updateEnrollmentStatus(id, enrollmentId, status);
    }

    // Module management
    @PostMapping("/{id}/modules")
    public Module createModule(@PathVariable Long id, @RequestBody @Valid Module module) {
        return groupService.createModule(id, module);
    }

    @GetMapping("/{id}/modules")
    public List<Module> getModules(@PathVariable Long id) {
        return groupService.getModules(id);
    }

    @DeleteMapping("/{id}/modules/{moduleId}")
    public void deleteModule(@PathVariable Long id, @PathVariable Long moduleId) {
        groupService.deleteModule(id, moduleId);
    }
}