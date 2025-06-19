package com.cuatico.campus.entities;

import java.util.List;

public interface Staff {
	List<Group> getTeacherGroups();
    void setTeacherGroups(List<Group> groups);
}
