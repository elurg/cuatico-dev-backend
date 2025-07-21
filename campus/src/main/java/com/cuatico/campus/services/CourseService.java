package com.cuatico.campus.services;

import java.util.List;

import com.cuatico.campus.entities.Course;

public interface CourseService {
	
	Course createCourse(Course course);
    List<Course> findAll();
    Course findById(Long id);
    Course updateCourse(Long id, Course course);
    void deleteCourse(Long id);
}
