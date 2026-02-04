package com.example.demo.services.implement;

import com.example.demo.dto.CandidateProfileDTO;
import com.example.demo.dto.SkillDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.CandidateProfileMapper;
import com.example.demo.mapper.ExperienceMapper;
import com.example.demo.mapper.ResumeMapper;
import com.example.demo.model.CandidateProfile;
import com.example.demo.model.Experience;
import com.example.demo.model.Resume;
import com.example.demo.model.Skill;
import com.example.demo.model.User;
import com.example.demo.repository.CandidateProfileRepository;
import com.example.demo.repository.SkillRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.CandidateProfileRequest;
import com.example.demo.services.CandidateProfileService;

import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CandidateProfileServiceImpl implements CandidateProfileService {

    private final CandidateProfileRepository candidateProfileRepository;
    private final UserRepository userRepository;
    private final CandidateProfileMapper candidateProfileMapper;
    private final ExperienceMapper experienceMapper;
    private final ResumeMapper resumeMapper;
    private final SkillRepository skillRepository;

    @Override
    public CandidateProfileDTO getCandidateProfile() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return candidateProfileRepository.findByUserEmail(username)
                .map(candidateProfileMapper::toDto)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Candidate profile not found with username: " + username));
    }

    @Override
    public CandidateProfileDTO createCandidateProfile(CandidateProfileDTO candidateProfileDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getCandidateProfile() != null) {
            throw new IllegalStateException("User already has a candidate profile.");
        }

        CandidateProfile profile = candidateProfileMapper.toEntity(candidateProfileDTO);
        profile.setUser(user);

        // create a new experience
        if (candidateProfileDTO.getExperiences() != null && !candidateProfileDTO.getExperiences().isEmpty()) {
            List<Experience> experiences = experienceMapper.toEntityList(candidateProfileDTO.getExperiences());
            experiences.forEach(exp -> exp.setProfile(profile));
            profile.setExperiences(experiences);
        }

        // create a new resume
        if (candidateProfileDTO.getResumes() != null && !candidateProfileDTO.getResumes().isEmpty()) {
            List<Resume> resumes = resumeMapper.toResumeList(candidateProfileDTO.getResumes());
            resumes.forEach(resume -> resume.setProfile(profile));
            profile.setResumes(resumes);
        }

        // map with current skill
        Set<Long> skillIds = candidateProfileDTO.getSkills()
                .stream()
                .map(SkillDTO::getId)
                .filter(id -> id != null && id > 0)
                .collect(Collectors.toSet());

        Set<Skill> skills = new HashSet<>(skillRepository.findAllById(skillIds));

        if (skills.size() != skillIds.size()) {
            throw new IllegalArgumentException("One or more skills do not exist");
        }

        profile.setSkills(skills);
        return candidateProfileMapper.toDto(candidateProfileRepository.save(profile));
    }

    @Override
    @Transactional
    public CandidateProfileDTO updateCandidateProfile(CandidateProfileRequest candidateProfileDTO) {
        // validateOwnership(id);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        CandidateProfile existingProfile = candidateProfileRepository.findByUserEmail(username)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Candidate profile not found with username: " + username));

        // update field basic information
        existingProfile.setFullName(candidateProfileDTO.getFullName());
        existingProfile.setPhone(candidateProfileDTO.getPhone());
        existingProfile.setSummary(candidateProfileDTO.getSummary());

        // Update skills
        if (candidateProfileDTO.getSkills() != null) {
            Set<Long> skillIds = candidateProfileDTO.getSkills().stream()
                    .map(SkillDTO::getId)
                    .filter(skillId -> skillId != null && skillId > 0)
                    .collect(Collectors.toSet());
            Set<Skill> skills = new HashSet<>(skillRepository.findAllById(skillIds));
            if (skills.size() != skillIds.size()) {
                throw new ResourceNotFoundException("One or more skills not found.");
            }
            existingProfile.setSkills(skills);
        }
        return candidateProfileMapper.toDto(candidateProfileRepository.save(existingProfile));
    }
}
