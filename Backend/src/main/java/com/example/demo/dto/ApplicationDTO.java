package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.example.demo.model.ApplicationStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApplicationDTO {
    private Long applicationId;

    private Long candidateId;

    private String email;

    private String fullName;

    private String resumeUrl;

    private Set<SkillDTO> skills;

    private ApplicationStatus status;

    private LocalDateTime appliedAt;
}
