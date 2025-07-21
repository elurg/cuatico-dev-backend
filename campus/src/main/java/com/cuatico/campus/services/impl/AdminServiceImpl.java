package com.cuatico.campus.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cuatico.campus.entities.Admin;
import com.cuatico.campus.entities.Group;
import com.cuatico.campus.entities.User;
import com.cuatico.campus.repositories.AdminRepository;
import com.cuatico.campus.repositories.GroupRepository;
import com.cuatico.campus.services.AdminService;
import com.cuatico.campus.services.ServiceException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{
	private final AdminRepository adminRepo;
	private final GroupRepository groupRepo;
	
	
	
//	----------REGISTRAR ADMIN-------------
	
	@Override
    @Transactional
    public Admin registerAdmin(Admin admin) {
        adminRepo.findByEmail(admin.getEmail()).ifPresent(t -> {
            throw new ServiceException("El admin con email " + admin.getEmail() + " ya está registrado.");
        });
        return adminRepo.save(admin);
    }
	
	
//	----------ACTUALIZAR ADMIN-------------
	
	@Override
    @Transactional
    public Admin updateAdmin(Long id, Admin updatedAdmin) {
        Admin existingAdmin = adminRepo.findById(id)
            .orElseThrow(() -> new ServiceException("El admin no existe"));
        existingAdmin.setName(updatedAdmin.getName());
        existingAdmin.setSurnames(updatedAdmin.getSurnames());
        existingAdmin.setUsername(updatedAdmin.getUsername());
        existingAdmin.setPhone(updatedAdmin.getPhone());

        if (!updatedAdmin.getEmail().equals(updatedAdmin.getEmail())) {
            adminRepo.findByEmail(updatedAdmin.getEmail()).ifPresent(t -> {
                throw new ServiceException("El email ya está en uso");
            });
        }
        existingAdmin.setEmail(updatedAdmin.getEmail());
        return adminRepo.save(existingAdmin);
    }
	
//	----------DESACTIVAR ADMIN-------------
	
	@Override
    @Transactional
    public void deactivateAdmin(Long id) {
        Admin checkedAdmin = adminRepo.findById(id)
            .orElseThrow(() -> new ServiceException("El admin no existe"));
        checkedAdmin.setStatus(User.Status.INACTIVE);
        adminRepo.save(checkedAdmin);
    }
	
	
//	----------CAMBIAR CONTRASEÑA-------------

	@Override
	public void changePassword(Long adminId, String oldPassword, String newPassword) {
		Admin checkedAdmin = adminRepo.findById(adminId)
				.orElseThrow(() -> new ServiceException("El admin no existe"));

//		PRIMERA APROXIMACIÓN PARA NO TENER QUE ACTIVAR SPRING SECURITY AÚN

		if (!checkedAdmin.getPasswordHash().equals(oldPassword)) {
			throw new ServiceException("La contraseña actual no es correcta");
		}

		checkedAdmin.setPasswordHash(newPassword);

		adminRepo.save(checkedAdmin);
	}
	
	
//	----------AÑADIR GRUPO AL ADMIN-------------

	@Override
    @Transactional
    public void addGroupToAdmin(Long adminId, Long groupId) {
        Admin chechedAdmin = adminRepo.findById(adminId)
            .orElseThrow(() -> new ServiceException("El admin no existe"));
        Group checkedGroup = groupRepo.findById(groupId)
            .orElseThrow(() -> new ServiceException("El grupo no existe"));

        if (!chechedAdmin.getGroups().contains(checkedGroup)) {
        	chechedAdmin.getGroups().add(checkedGroup);
        }
        if (!checkedGroup.getGroupStaff().contains(chechedAdmin)) {
        	checkedGroup.getGroupStaff().add(chechedAdmin);
        }
        adminRepo.save(chechedAdmin);
        groupRepo.save(checkedGroup);
    }
	
	
//	----------ELIMINAR GRUPO AL ADMIN-------------
	
	@Override
    @Transactional
    public void removeGroupFromAdmin(Long adminId, Long groupId) {
        Admin checkedAdmin = adminRepo.findById(adminId)
            .orElseThrow(() -> new ServiceException("El admin no existe"));
        Group checkedGroup = groupRepo.findById(groupId)
            .orElseThrow(() -> new ServiceException("El grupo no existe"));

        checkedAdmin.getGroups().remove(checkedGroup);
        checkedGroup.getGroupStaff().remove(checkedAdmin);

        adminRepo.save(checkedAdmin);
        groupRepo.save(checkedGroup);
    }
	
	
	
//	----------MOSTRAR GRUPOS DEL ADMIN-------------
	
	@Override
    @Transactional
    public List<Group> getAdminGroups(Long adminId) {
        Admin admin = adminRepo.findById(adminId)
            .orElseThrow(() -> new ServiceException("El admin no existe"));
        return admin.getGroups();
    }
	
	
//	----------BUSCAR ADMIN POR ID-------------
	
	@Override
    @Transactional
    public Admin findById(Long id) {
        return adminRepo.findById(id)
            .orElseThrow(() -> new ServiceException("El admin no existe"));
    }
	
	
//	----------MOSTRAR TODOS LOS ADMINS-------------

	@Override
    @Transactional
    public List<Admin> findAll() {
        List<Admin> admins = adminRepo.findAll();
        if (admins.isEmpty()) {
            throw new ServiceException("No hay profesores en la base de datos");
        }
        return admins;
    }
}