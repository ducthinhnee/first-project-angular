package com.example.demo.services.implement;

import com.example.demo.dto.SkillDTO;
import com.example.demo.mapper.SkillMapper;
import com.example.demo.model.Skill;
import com.example.demo.repository.SkillRepository;
import com.example.demo.services.SkillService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;

    public SkillServiceImpl(SkillRepository skillRepository, SkillMapper skillMapper) {
        this.skillRepository = skillRepository;
        this.skillMapper = skillMapper;
    }

    @Override
    public List<SkillDTO> getAllSkills() {
        return skillRepository.findAll().stream()
                .map(skillMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SkillDTO createSkill(SkillDTO skillDTO) {
        Skill skill = skillMapper.toEntity(skillDTO);
        return skillMapper.toDto(skillRepository.save(skill));
    }
}
