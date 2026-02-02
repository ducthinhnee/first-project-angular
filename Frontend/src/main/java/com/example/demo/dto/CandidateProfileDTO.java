package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateProfileDTO {
    private Long id;
    private String fullName;
    private String phone;
    private String summary;
    private Set<SkillDTO> skills;
    private List<ExperienceDTO> experiences;
    private List<ResumeDTO> resumes;
}
