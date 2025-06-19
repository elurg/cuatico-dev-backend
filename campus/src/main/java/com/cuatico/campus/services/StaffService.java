package com.cuatico.campus.services;

import java.util.List;

import com.cuatico.campus.entities.Group;
import com.cuatico.campus.entities.Staff;

public interface StaffService {
	
	void addGroupToStaff(Long staffId, Long groupId);
    void removeGroupFromStaff(Long staffId, Long groupId);
    List<Group> getStaffGroups(Long staffId);
    
    Staff findById(Long id);
    List<Staff> findAll();
}
