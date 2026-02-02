package com.example.demo.services;

import com.example.demo.dto.ResumeDTO;

public interface ResumeService {
    ResumeDTO getResumeById(Long id);

    ResumeDTO createForCandidate(Long candidateId, ResumeDTO resumeDTO);

    ResumeDTO update(ResumeDTO request);

    void delete(Long id);
}
