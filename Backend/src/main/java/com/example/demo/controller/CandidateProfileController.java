package com.example.demo.controller;

import com.example.demo.dto.CandidateProfileDTO;
import com.example.demo.request.ApplyJobRequest;
import com.example.demo.request.CandidateProfileRequest;
import com.example.demo.services.CandidateProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

        @GetMapping()
        @PreAuthorize("hasRole('CANDIDATE')")
        @Operation(summary = "Get a candidate profile by ID", description = "Retrieves a candidate profile. Requires CANDIDATE role.", security = @SecurityRequirement(name = "bearerAuth"))
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Profile found"),
                        @ApiResponse(responseCode = "403", description = "Forbidden"),
                        @ApiResponse(responseCode = "404", description = "Profile not found")
        })
        public ResponseEntity<CandidateProfileDTO> getCandidateProfile() {
                CandidateProfileDTO candidateProfileDTO = candidateProfileService.getCandidateProfile();
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

        @PutMapping()
        @PreAuthorize("hasRole('CANDIDATE')")
        @Operation(summary = "Update a candidate profile", description = "Updates an existing candidate profile. Requires CANDIDATE role.", security = @SecurityRequirement(name = "bearerAuth"))
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Profile updated successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid input"),
                        @ApiResponse(responseCode = "403", description = "Forbidden"),
                        @ApiResponse(responseCode = "404", description = "Profile not found")
        })
        public ResponseEntity<CandidateProfileDTO> updateCandidateProfile(
                        @RequestBody CandidateProfileRequest candidateProfileReq) {
                CandidateProfileDTO updatedProfile = candidateProfileService
                                .updateCandidateProfile(candidateProfileReq);
                return ResponseEntity.ok(updatedProfile);
        }
}
