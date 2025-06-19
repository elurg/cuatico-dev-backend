package com.cuatico.campus.services;

import java.util.List;

import com.cuatico.campus.entities.Group;
import com.cuatico.campus.entities.Teacher;

public interface TeacherService {
	
	Teacher registerTeacher(Teacher teacher);
    Teacher updateTeacher(Long id, Teacher updatedTeacher);
    void deactivateTeacher(Long id);
	public void changePassword(Long studentId, String oldPassword, String newPassword);

    void addGroupToTeacher(Long teacherId, Long groupId);
    void removeGroupFromTeacher(Long teacherId, Long groupId);
    List<Group> getTeacherGroups(Long teacherId);
    
    Teacher findById(Long id);
    List<Teacher> findAll();
}
