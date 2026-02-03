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

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/resumes")
@RequiredArgsConstructor
@Tag(name = "Resume", description = "APIs for managing resumes")
public class ResumeController {

    private final ResumeService resumeService;

    @GetMapping
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Get a resume", description = "Retrieves a specific resume. Requires CANDIDATE role.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resume found"),
            @ApiResponse(responseCode = "403", description = "Forbidden - user does not have the required role"),
            @ApiResponse(responseCode = "404", description = "Resume not found")
    })
    public ResponseEntity<List<ResumeDTO>> getResume() {
        return ResponseEntity.ok(resumeService.getResume());
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Create a resume for a candidate", description = "Creates a new resume for a specific candidate. Requires CANDIDATE role.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resume created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "403", description = "Forbidden - user does not have the required role"),
            @ApiResponse(responseCode = "404", description = "Candidate not found")
    })
    public ResponseEntity<?> uploadResume(
            @RequestParam MultipartFile file) {
        return new ResponseEntity<>(resumeService.uploadResume(file), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Delete resume by ID (Requires matching profileId param)", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<?> deleteResume(@PathVariable Long id) {
        resumeService.delete(id);
        return ResponseEntity.ok(Map.of("status", "ok", "message", "Resume deleted successfully"));
    }
}
