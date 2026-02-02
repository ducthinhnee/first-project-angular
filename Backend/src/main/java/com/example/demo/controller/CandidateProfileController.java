package com.example.demo.controller;

import com.example.demo.dto.CandidateProfileDTO;
import com.example.demo.services.CandidateProfileService;
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
@RequestMapping("/api/v1/candidate-profiles")
@RequiredArgsConstructor
@Tag(name = "Candidate Profile", description = "APIs for managing candidate profiles")
public class CandidateProfileController {

    private final CandidateProfileService candidateProfileService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Get a candidate profile by ID", description = "Retrieves a candidate profile. Requires CANDIDATE role.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile found"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Profile not found")
    })
    public ResponseEntity<CandidateProfileDTO> getCandidateProfile(
            @Parameter(description = "ID of the candidate profile") @PathVariable Long id) {
        CandidateProfileDTO candidateProfileDTO = candidateProfileService.getCandidateProfile(id);
        return ResponseEntity.ok(candidateProfileDTO);
    }

    @PostMapping
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Create a candidate profile", description = "Creates a new candidate profile. Requires CANDIDATE role.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<CandidateProfileDTO> createCandidateProfile(
            @RequestBody CandidateProfileDTO candidateProfileDTO) {
        return ResponseEntity.ok(candidateProfileService.createCandidateProfile(candidateProfileDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Update a candidate profile", description = "Updates an existing candidate profile. Requires CANDIDATE role.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Profile not found")
    })
    public ResponseEntity<CandidateProfileDTO> updateCandidateProfile(
            @Parameter(description = "ID of the profile to update") @PathVariable Long id,
            @RequestBody CandidateProfileDTO candidateProfileDTO) {
        CandidateProfileDTO updatedProfile = candidateProfileService.updateCandidateProfile(id, candidateProfileDTO);
        return ResponseEntity.ok(updatedProfile);
    }
}
