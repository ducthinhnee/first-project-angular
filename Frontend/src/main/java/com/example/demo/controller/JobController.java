package com.example.demo.controller;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.JobDTO;
import com.example.demo.model.JobLevel;
import com.example.demo.model.JobType;
import com.example.demo.request.JobRequest;
import com.example.demo.services.JobService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
@Tag(name = "Job", description = "APIs for managing jobs")
public class JobController {
        private final JobService jobService;

        @PostMapping
        @PreAuthorize("hasRole('EMPLOYER')")
        @Operation(summary = "Create a new job", description = "Creates a new job posting.", security = @SecurityRequirement(name = "bearerAuth"))
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Job created successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid input")
        })
        public JobDTO create(@Valid @RequestBody JobRequest request) {
                return jobService.create(request);
        }

        @PutMapping("/{id}")
        @PreAuthorize("hasRole('EMPLOYER')")
        @Operation(summary = "Update an existing job", description = "Updates a job posting by its ID.", security = @SecurityRequirement(name = "bearerAuth"))
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Job updated successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid input"),
                        @ApiResponse(responseCode = "404", description = "Job not found")
        })
        public JobDTO update(
                        @Parameter(description = "ID of the job to update") @PathVariable Long id,
                        @Valid @RequestBody JobRequest request) {
                return jobService.update(id, request);
        }

        @DeleteMapping("/{id}")
        @PreAuthorize("hasRole('EMPLOYER')")
        @Operation(summary = "Delete a job", description = "Deletes a job posting by its ID.", security = @SecurityRequirement(name = "bearerAuth"))
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Job deleted successfully"),
                        @ApiResponse(responseCode = "404", description = "Job not found")
        })
        public void delete(@Parameter(description = "ID of the job to delete") @PathVariable Long id) {
                jobService.delete(id);
        }

        // @GetMapping("/")
        // @GetMapping
        // public String getMethodName(@RequestParam String param) {
        // return new String();
        // }

        @GetMapping
        @Operation(summary = "Search for jobs", description = "Searches for jobs with pagination and filtering options.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Jobs found successfully")
        })
        public Page<JobDTO> search(
                        @Parameter(description = "Keyword to search in job title or description") @RequestParam(required = false) String keyword,
                        @Parameter(description = "Location to filter jobs") @RequestParam(required = false) String location,
                        @Parameter(description = "Type of job") @RequestParam(required = false) JobType jobType,
                        @Parameter(description = "Level of job") @RequestParam(required = false) JobLevel level,
                        @Parameter(description = "Minimum salary") @RequestParam(required = false) BigDecimal minSalary,
                        @Parameter(description = "Maximum salary") @RequestParam(required = false) BigDecimal maxSalary,
                        @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
                        @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "10") int size) {
                Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
                return jobService.search(keyword, location, jobType, level, minSalary, maxSalary, pageable);
        }

        @GetMapping("/{id}")
        @Operation(summary = "Get job by ID", description = "Retrieves a job by its ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Job found successfully"),
                        @ApiResponse(responseCode = "404", description = "Job not found")
        })
        public ResponseEntity<JobDTO> getById(
                        @Parameter(description = "Job ID") @PathVariable Long id) {
                JobDTO job = jobService.getById(id);
                return ResponseEntity.ok(job);
        }
}
