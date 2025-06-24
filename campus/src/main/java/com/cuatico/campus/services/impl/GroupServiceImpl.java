package com.cuatico.campus.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cuatico.campus.entities.Enrollment;
import com.cuatico.campus.entities.Group;
import com.cuatico.campus.entities.Module;
import com.cuatico.campus.entities.Staff;
import com.cuatico.campus.repositories.EnrollmentRepository;
import com.cuatico.campus.repositories.GroupRepository;
import com.cuatico.campus.repositories.ModuleRepository;
import com.cuatico.campus.repositories.StaffRepository;
import com.cuatico.campus.services.GroupService;
import com.cuatico.campus.services.ServiceException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

	private final GroupRepository groupRepo;
	private final StaffRepository staffRepo;
	private final EnrollmentRepository enrollmentRepo;
	private final ModuleRepository moduleRepo;

//  ----------------GESTIÓN DE GRUPO----------------

	@Override
	@Transactional
	public Group createGroup(Group group) {
		return groupRepo.save(group);
	}

	@Override
	@Transactional
	public Group updateGroup(Long groupId, Group updatedGroup) {
		Group checkedGroup = groupRepo.findById(groupId).orElseThrow(() -> new ServiceException("El grupo no existe"));
		checkedGroup.setName(updatedGroup.getName());
		checkedGroup.setStatus(updatedGroup.getStatus());
		checkedGroup.setType(updatedGroup.getType());
		checkedGroup.setStartDate(updatedGroup.getStartDate());
		checkedGroup.setEndDate(updatedGroup.getEndDate());
		checkedGroup.setTimetable(updatedGroup.getTimetable());
		checkedGroup.setSlackURL(updatedGroup.getSlackURL());
		checkedGroup.setMaxStudents(updatedGroup.getMaxStudents());
		checkedGroup.setActiveEnrolled(updatedGroup.getActiveEnrolled());
		checkedGroup.setInactiveEnrolled(updatedGroup.getInactiveEnrolled());
		checkedGroup.setCourse(updatedGroup.getCourse());
		return groupRepo.save(checkedGroup);
	}

//  IMPORTANTE! INTENTAR NO UTILIZAR PORQUE ELIMINA LA TRAZA DEL GRUPO. MEJOR UTILIZAR updateStatus

	@Override
	@Transactional
	public void deleteGroup(Long groupId) {
		groupRepo.deleteById(groupId);
	}
	
//  DEJA EL GRUPO INHABILITADO PERO CONSERVA SU TRAZA EN DB

	@Override
	@Transactional
	public void changeStatus(Long groupId, Group.Status newStatus) {
		Group checkedGroup = findById(groupId);
		checkedGroup.setStatus(newStatus);
		groupRepo.save(checkedGroup);
	}

	@Override
	@Transactional
	public Group findById(Long groupId) {
		return groupRepo.findById(groupId).orElseThrow(() -> new ServiceException("El grupo no existe"));
	}

	@Override
	@Transactional
	public List<Group> findAll() {
		return groupRepo.findAll();
	}

//  ----------------GESTIÓN DE STAFF----------------    

	@Override
	@Transactional
	public void addStaff(Long groupId, Long staffId) {
		Group checkedGroup = findById(groupId);
		Staff checkedStaff = staffRepo.findById(staffId).orElseThrow(() -> new ServiceException("El staff no existe"));
		if (!checkedGroup.getGroupStaff().contains(checkedStaff)) {
			checkedGroup.getGroupStaff().add(checkedStaff);
		}
		if (!checkedStaff.getGroups().contains(checkedGroup)) {
			checkedStaff.getGroups().add(checkedGroup);
		}
		groupRepo.save(checkedGroup);
		staffRepo.save(checkedStaff);
	}

	@Override
	@Transactional
	public void removeStaff(Long groupId, Long staffId) {
		Group checkedGroup = findById(groupId);
		Staff checkedStaff = staffRepo.findById(staffId).orElseThrow(() -> new ServiceException("El staff no existe"));
		checkedGroup.getGroupStaff().remove(checkedStaff);
		checkedStaff.getGroups().remove(checkedGroup);
		groupRepo.save(checkedGroup);
		staffRepo.save(checkedStaff);
	}

//  ----------------GESTIÓN DE MATRÍCULAS----------------    
	@Override
	@Transactional
	public void addEnrollment(Long groupId, Enrollment enrollment) {
	    Group group = findById(groupId);

	    if (!group.getGroupEnrollments().contains(enrollment)) {
	        enrollment.setGroup(group);
	        enrollmentRepo.save(enrollment);
	        group.getGroupEnrollments().add(enrollment);
	        groupRepo.save(group);

	        if (enrollment.getStudent() != null && !enrollment.getStudent().getStudentEnrollments().contains(enrollment)) {
	            enrollment.getStudent().getStudentEnrollments().add(enrollment);
	        }
	    }
	}

//    IMPORTANTE! INTENTAR NO UTILIZAR PORQUE ELIMINA LA TRAZA DE LA MATRICULA. MEJOR UTILIZAR updateEnrollmentStatus

	@Override
	@Transactional
	public void removeEnrollment(Long groupId, Long enrollmentId) {

		Group checkedGroup = findById(groupId);
		Enrollment checkedEnrollment = enrollmentRepo.findById(enrollmentId)
				.orElseThrow(() -> new ServiceException("La matrícula no existe"));
		if (checkedGroup.getGroupEnrollments().contains(checkedEnrollment)) {
			checkedGroup.getGroupEnrollments().remove(checkedEnrollment);
		}

		if (checkedEnrollment.getStudent() != null) {
			checkedEnrollment.getStudent().getStudentEnrollments().remove(checkedEnrollment);
		}

		checkedEnrollment.setGroup(null);
		enrollmentRepo.delete(checkedEnrollment);
		groupRepo.save(checkedGroup);
	}

//    DEJA LA MATRÍCULA INHABILITADA PERO CONSERVA SU TRAZA EN DB

	@Override
	@Transactional
	public boolean updateEnrollmentStatus(Long groupId, Long enrollmentId, Enrollment.Status newStatus) {
		Enrollment checkedEnrollment = enrollmentRepo.findById(enrollmentId)
				.orElseThrow(() -> new ServiceException("La matrícula no existe"));

		checkedEnrollment.setStatus(newStatus);
		enrollmentRepo.save(checkedEnrollment);
		return true;
	}

//  ----------------GESTIÓN DE MÓDULOS----------------    

	@Override
	@Transactional
	public Module createModule(Long groupId, Module module) {
		Group checkedGroup = groupRepo.findById(groupId).orElseThrow(() -> new ServiceException("El grupo no existe"));
		module.setGroup(checkedGroup);
		Module savedModule = moduleRepo.save(module);
		checkedGroup.getModules().add(savedModule);
		groupRepo.save(checkedGroup);
		return savedModule;
	}

	@Override
	@Transactional
	public List<Module> getModules(Long groupId) {
		Group checkedGroup = groupRepo.findById(groupId).orElseThrow(() -> new ServiceException("El grupo no existe"));
		return checkedGroup.getModules();
	}

	@Override
	@Transactional
	public void deleteModule(Long groupId, Long moduleId) {
		Group checkedGroup = groupRepo.findById(groupId).orElseThrow(() -> new ServiceException("El grupo no existe"));
		Module checkedModule = moduleRepo.findById(moduleId)
				.orElseThrow(() -> new ServiceException("El módulo no existe"));
		checkedGroup.getModules().remove(checkedModule);
		moduleRepo.delete(checkedModule);
		groupRepo.save(checkedGroup);
	}
}
