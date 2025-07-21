package com.cuatico.campus.services;

import java.util.List;

import com.cuatico.campus.entities.Group;

public interface GroupService {
	
    Group createGroup(Group group);
    Group updateGroup(Long groupId, Group updatedGroup);
    void deleteGroup(Long groupId);
    Group findById(Long groupId);
    List<Group> findAll();
    void changeStatus(Long groupId, Group.Status newStatus);    
}