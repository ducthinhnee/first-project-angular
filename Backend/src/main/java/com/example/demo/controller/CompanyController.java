package com.example.demo.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ApplicationDTO;
import com.example.demo.services.ApplicationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/company")
@RequiredArgsConstructor
@Tag(name = "Company", description = "APIs for managing company")
public class CompanyController {
    private final ApplicationService applicationService;

    @GetMapping("/jobs/{jobId}/applicants")
    @PreAuthorize("hasRole('EMPLOYER')")
    @Operation(summary = "Get applicants of a job", security = @SecurityRequirement(name = "bearerAuth"))
    public List<ApplicationDTO> getApplicants(@PathVariable Long jobId) {
        return applicationService.getApplicantsOfJob(jobId);
    }
}
