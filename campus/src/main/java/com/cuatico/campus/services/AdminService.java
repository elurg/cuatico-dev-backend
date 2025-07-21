package com.cuatico.campus.services;

import java.util.List;

import com.cuatico.campus.entities.Admin;
import com.cuatico.campus.entities.Group;

public interface AdminService {
	
	Admin registerAdmin(Admin admin);
    Admin updateAdmin(Long id, Admin updatedAdmin);
    void deactivateAdmin(Long id);
	public void changePassword(Long adminId, String oldPassword, String newPassword);

    void addGroupToAdmin(Long adminId, Long groupId);
    void removeGroupFromAdmin(Long adminId, Long groupId);
    List<Group> getAdminGroups(Long adminId);
    
    Admin findById(Long id);
    List<Admin> findAll();
}
