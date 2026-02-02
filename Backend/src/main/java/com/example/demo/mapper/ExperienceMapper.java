package com.example.demo.mapper;

import com.example.demo.dto.ExperienceDTO;
import com.example.demo.model.Experience;
import com.example.demo.model.Job;
import com.example.demo.request.JobRequest;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ExperienceMapper {

    @Mapping(source = "profile.id", target = "profileId")
    ExperienceDTO toDto(Experience experience);

    @Mapping(target = "profile", ignore = true)
    @Mapping(target = "id", ignore = true)
    Experience toEntity(ExperienceDTO experienceDTO);

    List<Experience> toEntityList(List<ExperienceDTO> experienceDTOs);

    @Mapping(target = "profile", ignore = true)
    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Experience experience, ExperienceDTO experienceDTO);
}
