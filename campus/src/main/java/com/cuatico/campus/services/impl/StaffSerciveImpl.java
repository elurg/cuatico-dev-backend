package com.cuatico.campus.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cuatico.campus.entities.Group;
import com.cuatico.campus.entities.Staff;
import com.cuatico.campus.repositories.GroupRepository;
import com.cuatico.campus.repositories.StaffRepository;
import com.cuatico.campus.services.ServiceException;
import com.cuatico.campus.services.StaffService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StaffSerciveImpl implements StaffService {

	private final GroupRepository groupRepo;
	private final StaffRepository staffRepo;

//	----------AÑADIR GRUPO AL STAFF-------------

	@Override
	@Transactional
	public void addGroupToStaff(Long staffId, Long groupId) {
		Staff checkedStaff = staffRepo.findById(staffId)
				.orElseThrow(() -> new ServiceException("El staff no existe"));
		Group checkedGroup = groupRepo.findById(groupId).orElseThrow(() -> new ServiceException("El grupo no existe"));

		
		if (!checkedStaff.getGroups().contains(checkedGroup)) {
			checkedStaff.getGroups().add(checkedGroup);
		}
		if (!checkedGroup.getGroupStaff().contains(checkedStaff)) {
			checkedGroup.getGroupStaff().add(checkedStaff);
		}
		staffRepo.save(checkedStaff);
		groupRepo.save(checkedGroup);
	}

//	----------ELIMINAR GRUPO AL STAFF-------------

	@Override
	@Transactional
	public void removeGroupFromStaff(Long staffId, Long groupId) {
		Staff checkedStaff = staffRepo.findById(staffId)
				.orElseThrow(() -> new ServiceException("El staff no existe"));
		Group checkedGroup = groupRepo.findById(groupId).orElseThrow(() -> new ServiceException("El grupo no existe"));

		checkedStaff.getGroups().remove(checkedGroup);
		checkedGroup.getGroupStaff().remove(checkedStaff);

		staffRepo.save(checkedStaff);
		groupRepo.save(checkedGroup);
	}

//	----------MOSTRAR GRUPOS DEL STAFF-------------

	@Override
	@Transactional
	public List<Group> getStaffGroups(Long staffId) {
		Staff checkedStaff = staffRepo.findById(staffId)
				.orElseThrow(() -> new ServiceException("No existe un staff con el id " + staffId));
		return checkedStaff.getGroups();
	}

//	----------BUSCAR STAFF POR ID-------------

	@Override
	@Transactional
	public Staff findById(Long staffId) {
		return staffRepo.findById(staffId)
				.orElseThrow(() -> new ServiceException("No existe un staff con el id " + staffId));
	}

//	----------MOSTRAR TODOS LOS STAFF-------------

    @Override
    @Transactional
    public List<Staff> findAll() {
        return staffRepo.findAll();
    }
}