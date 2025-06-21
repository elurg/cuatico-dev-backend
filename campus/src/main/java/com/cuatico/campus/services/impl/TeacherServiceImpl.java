package com.cuatico.campus.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cuatico.campus.entities.*;
import com.cuatico.campus.repositories.GroupRepository;
import com.cuatico.campus.repositories.TeacherRepository;
import com.cuatico.campus.services.ServiceException;
import com.cuatico.campus.services.TeacherService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService{
	private final TeacherRepository teacherRepo;
	private final GroupRepository groupRepo;
	
	
	
//	----------REGISTRAR TEACHER-------------
	
	@Override
    @Transactional
    public Teacher registerTeacher(Teacher teacher) {
        teacherRepo.findByEmail(teacher.getEmail()).ifPresent(t -> {
            throw new ServiceException("El profesor con email " + teacher.getEmail() + " ya está registrado.");
        });
        return teacherRepo.save(teacher);
    }
	
	
//	----------ACTUALIZAR TEACHER-------------
	
	@Override
    @Transactional
    public Teacher updateTeacher(Long id, Teacher updatedTeacher) {
        Teacher existingTeacher = teacherRepo.findById(id)
            .orElseThrow(() -> new ServiceException("El profesor no existe"));
        existingTeacher.setName(updatedTeacher.getName());
        existingTeacher.setSurnames(updatedTeacher.getSurnames());
        existingTeacher.setUsername(updatedTeacher.getUsername());
        existingTeacher.setPhone(updatedTeacher.getPhone());

        if (!existingTeacher.getEmail().equals(updatedTeacher.getEmail())) {
            teacherRepo.findByEmail(updatedTeacher.getEmail()).ifPresent(t -> {
                throw new ServiceException("El email ya está en uso");
            });
        }
        existingTeacher.setEmail(updatedTeacher.getEmail());
        return teacherRepo.save(existingTeacher);
    }
	
//	----------DESACTIVAR TEACHER-------------
	
	@Override
    @Transactional
    public void deactivateTeacher(Long id) {
        Teacher checkedTeacher = teacherRepo.findById(id)
            .orElseThrow(() -> new ServiceException("El profesor no existe"));
        checkedTeacher.setStatus(User.Status.INACTIVE);
        teacherRepo.save(checkedTeacher);
    }
	
	
//	----------CAMBIAR CONTRASEÑA-------------

	@Override
	public void changePassword(Long teacherId, String oldPassword, String newPassword) {
		Teacher checkedTeacher = teacherRepo.findById(teacherId)
				.orElseThrow(() -> new ServiceException("El profesor no existe"));

//		PRIMERA APROXIMACIÓN PARA NO TENER QUE ACTIVAR SPRING SECURITY AÚN

		if (!checkedTeacher.getPasswordHash().equals(oldPassword)) {
			throw new ServiceException("La contraseña actual no es correcta");
		}

		checkedTeacher.setPasswordHash(newPassword);

		teacherRepo.save(checkedTeacher);
	}
	
	
//	----------AÑADIR GRUPO AL TEACHER-------------

	@Override
    @Transactional
    public void addGroupToTeacher(Long teacherId, Long groupId) {
        Teacher chechedTeacher = teacherRepo.findById(teacherId)
            .orElseThrow(() -> new ServiceException("El profesor no existe"));
        Group checkedGroup = groupRepo.findById(groupId)
            .orElseThrow(() -> new ServiceException("El grupo no existe"));

        if (!chechedTeacher.getGroups().contains(checkedGroup)) {
        	chechedTeacher.getGroups().add(checkedGroup);
        }
        if (!checkedGroup.getGroupStaff().contains(chechedTeacher)) {
        	checkedGroup.getGroupStaff().add(chechedTeacher);
        }
        teacherRepo.save(chechedTeacher);
        groupRepo.save(checkedGroup);
    }
	
	
//	----------ELIMINAR GRUPO AL TEACHER-------------
	
	@Override
    @Transactional
    public void removeGroupFromTeacher(Long teacherId, Long groupId) {
        Teacher checkedTeacher = teacherRepo.findById(teacherId)
            .orElseThrow(() -> new ServiceException("El profesor no existe"));
        Group checkedGroup = groupRepo.findById(groupId)
            .orElseThrow(() -> new ServiceException("El grupo no existe"));

        checkedTeacher.getGroups().remove(checkedGroup);
        checkedGroup.getGroupStaff().remove(checkedTeacher);

        teacherRepo.save(checkedTeacher);
        groupRepo.save(checkedGroup);
    }
	
	
	
//	----------MOSTRAR GRUPOS DEL TEACHER-------------
	
	@Override
    @Transactional
    public List<Group> getTeacherGroups(Long teacherId) {
        Teacher teacher = teacherRepo.findById(teacherId)
            .orElseThrow(() -> new ServiceException("El profesor no existe"));
        return teacher.getGroups();
    }
	
	
//	----------BUSCAR TEACHER POR ID-------------
	
	@Override
    @Transactional
    public Teacher findById(Long id) {
        return teacherRepo.findById(id)
            .orElseThrow(() -> new ServiceException("El profesor no existe"));
    }
	
	
//	----------MOSTRAR TODOS LOS TEACHERS-------------

	@Override
    @Transactional
    public List<Teacher> findAll() {
        List<Teacher> teachers = teacherRepo.findAll();
        if (teachers.isEmpty()) {
            throw new ServiceException("No hay profesores en la base de datos");
        }
        return teachers;
    }
}