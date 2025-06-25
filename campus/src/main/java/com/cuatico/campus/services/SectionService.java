package com.cuatico.campus.services;

import java.util.List;

import com.cuatico.campus.entities.Section;

public interface SectionService {
	Section createSection(Long groupId, Section section);
    List<Section> getSections(Long groupId);
    void deleteSection(Long groupId, Long sectionId);
}
