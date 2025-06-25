package com.cuatico.campus.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cuatico.campus.entities.Group;
import com.cuatico.campus.services.GroupService;
import com.cuatico.campus.services.ServiceException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

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
}