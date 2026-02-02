package com.example.demo.mapper;

import com.example.demo.dto.CandidateProfileDTO;
import com.example.demo.model.CandidateProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CandidateProfileMapper {

    CandidateProfileDTO toDto(CandidateProfile candidateProfile);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "experiences", ignore = true)
    @Mapping(target = "resumes", ignore = true)
    @Mapping(target = "id", ignore = true)
    CandidateProfile toEntity(CandidateProfileDTO candidateProfileDTO);
}
