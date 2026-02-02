package com.example.demo.mapper;

import com.example.demo.dto.SkillDTO;
import com.example.demo.model.Skill;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SkillMapper {

    SkillDTO toDto(Skill skill);

    Skill toEntity(SkillDTO skillDTO);
}
