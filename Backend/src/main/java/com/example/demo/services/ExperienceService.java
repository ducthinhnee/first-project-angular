package com.example.demo.services;

import com.example.demo.dto.ExperienceDTO;

public interface ExperienceService {
    ExperienceDTO createForCandidate(Long candidateId, ExperienceDTO request);

    ExperienceDTO update(ExperienceDTO request);

    void delete(Long id);
}
