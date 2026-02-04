package com.example.demo.services.implement;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ApplicationDTO;
import com.example.demo.dto.SkillDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Company;
import com.example.demo.repository.ApplicationRepository;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.services.ApplicationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ApplicationServiceImp implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final CompanyRepository companyRepository;

    @Override
    public List<ApplicationDTO> getApplicantsOfJob(Long jobId) {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Company company = companyRepository.findByUserEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        return applicationRepository
                .findApplicationsOfCompanyJob(jobId, company.getId())
                .stream()
                .map(app -> ApplicationDTO.builder()
                        .applicationId(app.getId())
                        .candidateId(app.getCandidate().getId())
                        .email(app.getCandidate().getEmail())
                        .fullName(app.getCandidate().getFullName())
                        .resumeUrl(app.getResume().getFileUrl())
                        .skills(app.getCandidate()
                                .getCandidateProfile()
                                .getSkills()
                                        .stream()
                                        .map(skill -> new SkillDTO(skill.getId(), skill.getName()))
                                        .collect(Collectors.toSet()))
                        .status(app.getStatus())
                        .appliedAt(app.getAppliedAt())
                        .build())
                .toList();
    }

}
