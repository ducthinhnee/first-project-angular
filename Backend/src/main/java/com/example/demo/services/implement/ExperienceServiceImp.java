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
    public ExperienceDTO createForCandidate(ExperienceDTO request) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        CandidateProfile profile = candidateProfileRepository.findByUserEmail(userEmail)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Candidate profile not found with emailemail: " + userEmail));
        Experience experience = experienceMapper.toEntity(request);
        experience.setProfile(profile);
        return experienceMapper.toDto(experienceRepository.save(experience));
    }

    @Override
    @Transactional
    public ExperienceDTO update(ExperienceDTO request) {
        Experience experience = experienceRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found with id: " + request.getId()));
        validateOwnership(request.getId());
        experienceMapper.updateEntity(experience, request);
        return experienceMapper.toDto(experience);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found with id: " + id));
        validateOwnership(id);
        experienceRepository.delete(experience);
    }

    @Override
    public List<ExperienceDTO> getExperiencesByProfileId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        CandidateProfile profile = candidateProfileRepository.findByUserEmail(username)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Candidate profile not found with email: " + username));

        return experienceRepository.findByProfileId(profile.getId()).stream()
                .map(experienceMapper::toDto)
                .collect(Collectors.toList());
    }

    private void validateOwnership(Long experienceId) {
        Experience experience = experienceRepository.findById(experienceId)
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found with id: " + experienceId));

        CandidateProfile profile = experience.getProfile();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!profile.getUser().getEmail().equals(username)) {
            throw new AccessDeniedException("You do not have permission to access this resource.");
        }
    }

}
