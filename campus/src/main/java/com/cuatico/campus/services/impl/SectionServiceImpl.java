package com.cuatico.campus.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cuatico.campus.entities.Group;
import com.cuatico.campus.entities.Section;
import com.cuatico.campus.repositories.GroupRepository;
import com.cuatico.campus.repositories.SectionRepository;
import com.cuatico.campus.services.SectionService;
import com.cuatico.campus.services.ServiceException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService {

	private final GroupRepository groupRepo;
	private final SectionRepository sectionRepo; 

	@Override
	@Transactional
	public Section createSection(Long groupId, Section section) {
		Group checkedGroup = groupRepo.findById(groupId).orElseThrow(() -> new ServiceException("El grupo no existe"));
		section.setGroup(checkedGroup);
		Section savedSection = sectionRepo.save(section);
		checkedGroup.getSections().add(savedSection);
		groupRepo.save(checkedGroup);
		return savedSection;
	}

	@Override
	@Transactional
	public List<Section> getSections(Long groupId) {
		Group checkedGroup = groupRepo.findById(groupId).orElseThrow(() -> new ServiceException("El grupo no existe"));
		return checkedGroup.getSections();
	}

	@Override
	@Transactional
	public void deleteSection(Long groupId, Long sectionId) {
		Group checkedGroup = groupRepo.findById(groupId).orElseThrow(() -> new ServiceException("El grupo no existe"));
		Section checkedSection = sectionRepo.findById(sectionId)
				.orElseThrow(() -> new ServiceException("El módulo no existe"));
		checkedGroup.getSections().remove(checkedSection);
		sectionRepo.delete(checkedSection);
		groupRepo.save(checkedGroup);
	}
}
