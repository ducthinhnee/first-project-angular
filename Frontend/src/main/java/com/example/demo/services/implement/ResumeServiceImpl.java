package com.example.demo.services.implement;

import com.example.demo.dto.ResumeDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.ResumeMapper;
import com.example.demo.model.CandidateProfile;
import com.example.demo.model.Resume;
import com.example.demo.repository.CandidateProfileRepository;
import com.example.demo.repository.ResumeRepository;
import com.example.demo.services.ResumeService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;
    private final CandidateProfileRepository candidateProfileRepository;
    private final ResumeMapper resumeMapper;

    public ResumeServiceImpl(ResumeRepository resumeRepository, CandidateProfileRepository candidateProfileRepository,
            ResumeMapper resumeMapper) {
        this.resumeRepository = resumeRepository;
        this.candidateProfileRepository = candidateProfileRepository;
        this.resumeMapper = resumeMapper;
    }

    @Override
    public ResumeDTO getResumeById(Long id) {
        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found"));
        validateOwnership(resume.getProfile().getId());
        return resumeMapper.toDto(resume);
    }

    @Override
    public ResumeDTO createForCandidate(Long candidateId, ResumeDTO resumeDTO) {
        validateOwnership(candidateId);
        CandidateProfile profile = candidateProfileRepository.findById(candidateId)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate profile not found"));

        Resume resume = resumeMapper.toEntity(resumeDTO);
        resume.setProfile(profile);
        return resumeMapper.toDto(resumeRepository.save(resume));
    }

    private void validateOwnership(Long profileId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        CandidateProfile userProfile = candidateProfileRepository.findByUserEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found for current user"));

        if (!userProfile.getId().equals(profileId)) {
            throw new AccessDeniedException("You do not have permission to access this resource.");
        }
    }

    @Override
    @Transactional
    public ResumeDTO update(ResumeDTO resumeDTO) {
        Resume resume = resumeRepository.findById(resumeDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found with id: " + resumeDTO.getId()));

        validateOwnership(resume.getProfile().getId());

        resumeMapper.updateEntity(resume, resumeDTO);
        return resumeMapper.toDto(resume);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found with id: " + id));
        validateOwnership(resume.getProfile().getId());
        resumeRepository.delete(resume);
    }
}
