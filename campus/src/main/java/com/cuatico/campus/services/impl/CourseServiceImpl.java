package com.cuatico.campus.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cuatico.campus.entities.Course;
import com.cuatico.campus.repositories.CourseRepository;
import com.cuatico.campus.services.CourseService;
import com.cuatico.campus.services.ServiceException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepo;

    @Override
    @Transactional
    public Course createCourse(Course course) {
        return courseRepo.save(course);
    }

    @Override
    @Transactional
    public List<Course> findAll() {
        return courseRepo.findAll();
    }

    @Override
    @Transactional
    public Course findById(Long id) {
        return courseRepo.findById(id)
            .orElseThrow(() -> new ServiceException("El curso no existe"));
    }

    @Override
    @Transactional
    public Course updateCourse(Long id, Course updatedCourse) {
        Course existing = courseRepo.findById(id)
        		.orElseThrow(() -> new ServiceException("El curso no existe"));
        existing.setTitle(updatedCourse.getTitle());
        existing.setDescription(updatedCourse.getDescription());
        existing.setPrice(updatedCourse.getPrice());
        return courseRepo.save(existing);
    }

    @Override
    @Transactional
    public void deleteCourse(Long id) {
        courseRepo.deleteById(id);
    }
}
