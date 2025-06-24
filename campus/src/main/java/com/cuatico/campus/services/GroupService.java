package com.cuatico.campus.services;

import java.util.List;

import com.cuatico.campus.entities.Enrollment;
import com.cuatico.campus.entities.Group;
import com.cuatico.campus.entities.Module;

public interface GroupService {
    // CRUD de grupo
    Group createGroup(Group group);
    Group updateGroup(Long groupId, Group updatedGroup);
    void deleteGroup(Long groupId);
    Group findById(Long groupId);
    List<Group> findAll();
    void changeStatus(Long groupId, Group.Status newStatus);

    // Gestión de staff
    void addStaff(Long groupId, Long staffId);
    void removeStaff(Long groupId, Long staffId);

    // Gestión de matrículas
    void addEnrollment(Long groupId, Enrollment enrollment);
    void removeEnrollment(Long groupId, Long enrollmentId);
    boolean updateEnrollmentStatus(Long groupId, Long enrollmentId, Enrollment.Status newStatus);

    // Gestión de módulos (solo relación con el grupo)
    Module createModule(Long groupId, Module module);
    List<Module> getModules(Long groupId);
    void deleteModule(Long groupId, Long moduleId);
}