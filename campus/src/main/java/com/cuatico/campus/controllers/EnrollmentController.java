package com.cuatico.campus.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.cuatico.campus.entities.Certificate;
import com.cuatico.campus.entities.Enrollment;
import com.cuatico.campus.services.EnrollmentService;
import com.cuatico.campus.services.ServiceException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping("")
    public Enrollment addEnrollment(@RequestBody @Valid Enrollment enrollment) {
        try {
            return enrollmentService.addEnrollment(enrollment);
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping("")
    public List<Enrollment> findAll() {
        return enrollmentService.findAll();
    }

    @GetMapping("/{id}")
    public Enrollment findById(@PathVariable Long id) {
        try {
            return enrollmentService.findById(id);
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    public void removeEnrollment(@PathVariable Long id) {
        try {
            enrollmentService.removeEnrollment(id);
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PatchMapping("/{id}/status")
    public void updateEnrollmentStatus(@PathVariable Long id, @RequestParam Enrollment.Status status) {
        try {
            enrollmentService.updateEnrollmentStatus(id, status);
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PostMapping("/{id}/certificate")
    public Certificate generateCertificate(@PathVariable Long id) {
        try {
            return enrollmentService.generateCertificate(id);
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}