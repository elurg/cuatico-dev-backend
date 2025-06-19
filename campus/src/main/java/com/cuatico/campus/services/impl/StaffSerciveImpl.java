package com.cuatico.campus.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cuatico.campus.entities.*;
import com.cuatico.campus.repositories.AdminRepository;
import com.cuatico.campus.repositories.GroupRepository;
import com.cuatico.campus.repositories.TeacherRepository;
import com.cuatico.campus.services.ServiceException;
import com.cuatico.campus.services.StaffService;
import com.cuatico.campus.services.TeacherService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StaffSerciveImpl implements StaffService {

	private final TeacherRepository teacherRepo;
	private final AdminRepository adminRepo;
	private final GroupRepository groupRepo;

//	----------AÑADIR GRUPO AL STAFF-------------

	@Override
	@Transactional
	public void addGroupToStaff(Long staffId, Long groupId) {
		Staff chechedStaff = teacherRepo.findById(staffId)
				.orElseThrow(() -> new ServiceException("El profesor no existe"));
		Group checkedGroup = groupRepo.findById(groupId).orElseThrow(() -> new ServiceException("El grupo no existe"));

		if (!chechedStaff.getTeacherGroups().contains(checkedGroup)) {
			chechedStaff.getTeacherGroups().add(checkedGroup);
		}
		if (!checkedGroup.getGroupTeachers().contains(chechedStaff)) {
			checkedGroup.getGroupTeachers().add(chechedStaff); //no podemos hacer esto porque group espera una lista de Teacher
		}
		if (chechedStaff instanceof Teacher) {
	        teacherRepo.save((Teacher) chechedStaff);
	    } else if (chechedStaff instanceof Admin) {
	        adminRepo.save((Admin) chechedStaff);
	    }
		groupRepo.save(checkedGroup);
	}

////	----------ELIMINAR GRUPO AL STAFF-------------
//
//	@Override
//	@Transactional
//	public void removeGroupFromStaff(Long teacherId, Long groupId) {
//		Teacher checkedTeacher = teacherRepo.findById(teacherId)
//				.orElseThrow(() -> new ServiceException("El profesor no existe"));
//		Group checkedGroup = groupRepo.findById(groupId).orElseThrow(() -> new ServiceException("El grupo no existe"));
//
//		checkedTeacher.getTeacherGroups().remove(checkedGroup);
//		checkedGroup.getGroupTeachers().remove(checkedTeacher);
//
//		teacherRepo.save(checkedTeacher);
//		groupRepo.save(checkedGroup);
//	}
//
////	----------MOSTRAR GRUPOS DEL STAFF-------------
//
//	@Override
//	@Transactional
//	public List<Group> getStaffGroups(Long staffId) {
//		Staff checkedStaff = teacherRepo.findById(staffId).map(t -> (Staff) t)
//				.or(() -> adminRepo.findById(staffId).map(a -> (Staff) a))
//				.orElseThrow(() -> new ServiceException("No existe un staff con el id " + staffId));
//		return teacher.getTeacherGroups();
//	}
//
////	----------BUSCAR STAFF POR ID-------------
//
//	@Override
//	@Transactional
//	public Staff findById(Long staffId) {
//		return teacherRepo.findById(staffId).map(t -> (Staff) t)
//				.or(() -> adminRepo.findById(staffId).map(a -> (Staff) a))
//				.orElseThrow(() -> new ServiceException("No existe un staff con el id " + staffId));
//	}
//
////	----------MOSTRAR TODOS LOS STAFF-------------
//
//    @Override
//    @Transactional
//    public List<Staff> findAll() {
//        List<Staff> allStaff = new ArrayList<>();
//        allStaff.addAll(teacherRepo.findAll());
//        allStaff.addAll(adminRepo.findAll());
//        if (allStaff.isEmpty()) {
//            throw new ServiceException("No hay ningún staff registrado.");
//        }
//        return allStaff;
//    }
//}
}