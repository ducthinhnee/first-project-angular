package com.example.demo.services.implement;

import com.example.demo.dto.CandidateProfileDTO;
import com.example.demo.dto.ExperienceDTO;
import com.example.demo.dto.ResumeDTO;
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
import com.example.demo.services.CandidateProfileService;

import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
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
    private final ExperienceServiceImp experienceService;
    private final ResumeServiceImpl resumeService;

    @Override
    public CandidateProfileDTO getCandidateProfile(Long id) {
        validateOwnership(id);
        return candidateProfileRepository.findById(id)
                .map(candidateProfileMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate profile not found with id: " + id));
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
    public CandidateProfileDTO updateCandidateProfile(Long id, CandidateProfileDTO candidateProfileDTO) {
        validateOwnership(id);
        CandidateProfile existingProfile = candidateProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate profile not found with id: " + id));

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

        // // Update experiences
        // if (candidateProfileDTO.getExperiences() != null) {
        //     syncExperiences(existingProfile, candidateProfileDTO.getExperiences());
        // }

        // // Update resumes
        // if (candidateProfileDTO.getResumes() != null) {
        //     syncResumes(existingProfile, candidateProfileDTO.getResumes());
        // }
        return candidateProfileMapper.toDto(candidateProfileRepository.save(existingProfile));
    }

    private void validateOwnership(Long profileId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getCandidateProfile() == null || !user.getCandidateProfile().getId().equals(profileId)) {
            throw new AccessDeniedException("You do not have permission to access this profile.");
        }
    }

    // private void syncExperiences(CandidateProfile profile, List<ExperienceDTO> dtos) {
    //     Map<Long, Experience> existingMap = profile.getExperiences().stream()
    //             .collect(Collectors.toMap(Experience::getId, Function.identity()));

    //     for (ExperienceDTO dto : dtos) {
    //         if (dto.getId() != null && existingMap.containsKey(dto.getId())) {
    //             // UPDATE
    //             experienceService.update(dto);
    //             existingMap.remove(dto.getId());
    //         } else {
    //             // INSERT
    //             experienceService.createForCandidate(profile.getId(), dto);
    //         }
    //     }

    //     // Any items left in the map are to be deleted
    //     existingMap.keySet().forEach(experienceId -> {
    //         profile.getExperiences().removeIf(
    //                 ex -> ex.getId() != null && ex.getId().equals(experienceId));
    //     });
    // }

    // private void syncResumes(CandidateProfile profile, List<ResumeDTO> dtos) {
    //     Map<Long, Resume> existingMap = profile.getResumes().stream()
    //             .collect(Collectors.toMap(Resume::getId, Function.identity()));

    //     for (ResumeDTO dto : dtos) {
    //         if (dto.getId() != null && existingMap.containsKey(dto.getId())) {
    //             // UPDATE
    //             resumeService.update(dto);
    //             existingMap.remove(dto.getId());
    //         } else {
    //             // INSERT
    //             resumeService.createForCandidate(profile.getId(), dto);
    //         }
    //     }

    //     // Any items left in the map are to be deleted
    //     existingMap.keySet().forEach(resumeId -> {
    //         profile.getResumes().removeIf(
    //                 resume -> resume.getId() != null && resume.getId().equals(resumeId));
    //     });
    // }
}
