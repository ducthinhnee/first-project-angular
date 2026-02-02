package com.example.demo.controller;

import com.example.demo.dto.ExperienceDTO;
import com.example.demo.services.ExperienceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/experiences")
@RequiredArgsConstructor
@Tag(name = "Experience", description = "APIs for managing candidate experiences")
public class ExperienceController {

    private final ExperienceService experienceService;

    @GetMapping
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Get experiences by profile ID", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<ExperienceDTO>> getExperiencesByProfileId() {
        return ResponseEntity.ok(experienceService.getExperiencesByProfileId());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Update experience by ID (Requires matching profileId in body)", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ExperienceDTO> updateExperience(@PathVariable Long id,
            @RequestBody ExperienceDTO experienceDTO) {
        return ResponseEntity.ok(experienceService.update(experienceDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Delete experience by ID (Requires matching profileId param)", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Void> deleteExperience(@PathVariable Long id, @RequestParam Long profileId) {
        experienceService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/candidate-profile")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Create experience for candidate", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ExperienceDTO> createExperience(@RequestBody ExperienceDTO experienceDTO) {
        return new ResponseEntity<>(experienceService.createForCandidate(experienceDTO),
                HttpStatus.CREATED);
    }
}
