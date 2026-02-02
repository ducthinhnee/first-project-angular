package com.example.demo.services;

import com.example.demo.dto.CandidateProfileDTO;

public interface CandidateProfileService {
    CandidateProfileDTO getCandidateProfile(Long id);
    CandidateProfileDTO createCandidateProfile(CandidateProfileDTO candidateProfileDTO);
    CandidateProfileDTO updateCandidateProfile(Long id, CandidateProfileDTO candidateProfileDTO);
}
