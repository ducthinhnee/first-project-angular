package com.example.demo.services;

import com.example.demo.dto.CandidateProfileDTO;
import com.example.demo.request.CandidateProfileRequest;

public interface CandidateProfileService {
    CandidateProfileDTO getCandidateProfile();

    CandidateProfileDTO createCandidateProfile(CandidateProfileDTO candidateProfileDTO);

    CandidateProfileDTO updateCandidateProfile(CandidateProfileRequest candidateProfileDTO);
}
