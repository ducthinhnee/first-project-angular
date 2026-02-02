package com.example.demo.services.implement;

import com.example.demo.dto.ExperienceDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.ExperienceMapper;
import com.example.demo.model.CandidateProfile;
import com.example.demo.model.Experience;
import com.example.demo.repository.CandidateProfileRepository;
import com.example.demo.repository.ExperienceRepository;
import com.example.demo.services.ExperienceService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExperienceServiceImp implements ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final ExperienceMapper experienceMapper;
    private final CandidateProfileRepository candidateProfileRepository;

    @Override
    @Transactional
    public ExperienceDTO createForCandidate(Long candidateId, ExperienceDTO request) {
        validateOwnership(candidateId);
        CandidateProfile profile = candidateProfileRepository.findById(candidateId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Candidate profile not found with id: " + candidateId));
        Experience experience = experienceMapper.toEntity(request);
        experience.setProfile(profile);
        return experienceMapper.toDto(experienceRepository.save(experience));
    }

    @Override
    @Transactional
    public ExperienceDTO update(ExperienceDTO request) {
        Experience experience = experienceRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found with id: " + request.getId()));
        validateOwnership(experience.getProfile().getId());
        experienceMapper.updateEntity(experience, request);
        return experienceMapper.toDto(experience);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found with id: " + id));
        validateOwnership(experience.getProfile().getId());
        experienceRepository.delete(experience);
    }

    @Override
    public List<ExperienceDTO> getExperiencesByProfileId(Long profileId) {
        validateOwnership(profileId);
        return experienceRepository.findByProfileId(profileId).stream()
                .map(experienceMapper::toDto)
                .collect(Collectors.toList());
    }

    private void validateOwnership(Long profileId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        CandidateProfile userProfile = candidateProfileRepository.findByUserEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found for current user"));

        if (!userProfile.getId().equals(profileId)) {
            throw new AccessDeniedException("You do not have permission to access this resource.");
        }
    }

}
