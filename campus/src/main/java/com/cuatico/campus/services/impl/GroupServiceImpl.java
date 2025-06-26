package com.cuatico.campus.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cuatico.campus.entities.Group;
import com.cuatico.campus.repositories.GroupRepository;
import com.cuatico.campus.services.GroupService;
import com.cuatico.campus.services.ServiceException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

	private final GroupRepository groupRepo;
//	private final StaffRepository staffRepo;

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
		var grupo = groupRepo.findById(groupId).orElseThrow(() -> new ServiceException("El grupo no existe"));
		// grupo.setGroupStaff(staffRepo.findByGroupsId(groupId));
		return grupo;
	}

	@Override
	@Transactional
	public List<Group> findAll() {
		return groupRepo.findAll();
	}
}
