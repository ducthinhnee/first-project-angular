package com.example.demo.services.implement;

import com.example.demo.dto.SkillDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.SkillMapper;
import com.example.demo.model.Skill;
import com.example.demo.repository.SkillRepository;
import com.example.demo.services.SkillService;

import jakarta.transaction.Transactional;

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
    public SkillDTO getSkillById(Long id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found with id: " + id));
        return skillMapper.toDto(skill);
    }

    @Override
    @Transactional
    public SkillDTO createSkill(SkillDTO skillDTO) {
        if (skillRepository.existsByName(skillDTO.getName())) {
            throw new RuntimeException("Skill already exists with name: " + skillDTO.getName());
        }
        Skill skill = new Skill();
        skill.setName(skillDTO.getName());
        Skill savedSkill = skillRepository.save(skill);
        return skillMapper.toDto(savedSkill);
    }

    @Override
    @Transactional
    public SkillDTO updateSkill(Long id, SkillDTO skillDTO) {
        Skill existingSkill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found with id: " + id));

        if (!existingSkill.getName().equals(skillDTO.getName()) && skillRepository.existsByName(skillDTO.getName())) {
            throw new RuntimeException("Skill already exists with name: " + skillDTO.getName());
        }

        existingSkill.setName(skillDTO.getName());
        Skill updatedSkill = skillRepository.save(existingSkill);
        return skillMapper.toDto(updatedSkill);
    }

    @Override
    @Transactional
    public void deleteSkill(Long id) {
        if (!skillRepository.existsById(id)) {
            throw new ResourceNotFoundException("Skill not found with id: " + id);
        }
        skillRepository.deleteById(id);
    }
}
