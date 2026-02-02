package com.example.demo.services;

import java.util.List;

import com.example.demo.dto.ExperienceDTO;

public interface ExperienceService {
    ExperienceDTO createForCandidate(ExperienceDTO request);

    ExperienceDTO update(ExperienceDTO request);

    void delete(Long id);

    public List<ExperienceDTO> getExperiencesByProfileId();
}
