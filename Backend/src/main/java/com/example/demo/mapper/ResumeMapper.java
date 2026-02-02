package com.example.demo.mapper;

import com.example.demo.dto.ResumeDTO;
import com.example.demo.model.Job;
import com.example.demo.model.Resume;
import com.example.demo.request.JobRequest;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ResumeMapper {

    ResumeDTO toDto(Resume resume);

    @Mapping(target = "profile", ignore = true)
    @Mapping(target = "id", ignore = true)
    Resume toEntity(ResumeDTO resumeDTO);

    List<Resume> toResumeList(List<ResumeDTO> resumeDTOs);

    @Mapping(target = "profile", ignore = true)
    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Resume resume, ResumeDTO resumeDTO);
}
