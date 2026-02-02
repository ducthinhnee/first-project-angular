package com.example.demo.services;

import com.example.demo.dto.SkillDTO;

import java.util.List;

public interface SkillService {
    List<SkillDTO> getAllSkills();

    SkillDTO getSkillById(Long id);

    SkillDTO createSkill(SkillDTO skillDTO);

    SkillDTO updateSkill(Long id, SkillDTO skillDTO);

    void deleteSkill(Long id);
}
