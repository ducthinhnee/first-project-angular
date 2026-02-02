package com.example.demo.services;

import com.example.demo.dto.SkillDTO;

import java.util.List;

public interface SkillService {
    List<SkillDTO> getAllSkills();
    SkillDTO createSkill(SkillDTO skillDTO);
}
