package com.example.demo.controller;

import com.example.demo.dto.ResumeDTO;
import com.example.demo.services.ResumeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Resume", description = "APIs for managing resumes")
public class ResumeController {

    private final ResumeService resumeService;

    @GetMapping("/resumes/{id}")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Get a resume by ID", description = "Retrieves a specific resume by its ID. Requires CANDIDATE role.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resume found"),
            @ApiResponse(responseCode = "403", description = "Forbidden - user does not have the required role"),
            @ApiResponse(responseCode = "404", description = "Resume not found")
    })
    public ResponseEntity<ResumeDTO> getResumeById(
            @Parameter(description = "ID of the resume to retrieve") @PathVariable Long id) {
        return ResponseEntity.ok(resumeService.getResumeById(id));
    }

    @PostMapping("/candidates/{candidateId}/resumes")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Create a resume for a candidate", description = "Creates a new resume for a specific candidate. Requires CANDIDATE role.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resume created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "403", description = "Forbidden - user does not have the required role"),
            @ApiResponse(responseCode = "404", description = "Candidate not found")
    })
    public ResponseEntity<ResumeDTO> createResumeForCandidate(
            @Parameter(description = "ID of the candidate to create the resume for") @PathVariable Long candidateId,
            @RequestBody ResumeDTO resumeDTO) {
        return ResponseEntity.ok(resumeService.createForCandidate(candidateId, resumeDTO));
    }
}
