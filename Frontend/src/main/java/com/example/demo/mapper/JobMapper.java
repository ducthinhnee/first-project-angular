package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.demo.dto.JobDTO;
import com.example.demo.model.Job;
import com.example.demo.request.JobRequest;

@Mapper(componentModel = "spring")
public interface JobMapper {

    @Mapping(source = "company.name", target = "companyName")
    JobDTO toDTO(Job job);

    List<JobDTO> toDTOList(List<Job> jobs);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "OPEN")
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Job toEntity(JobRequest request);

    @Mapping(target = "company", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Job job, JobRequest request);
}
