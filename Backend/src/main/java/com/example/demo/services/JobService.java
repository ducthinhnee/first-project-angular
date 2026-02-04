package com.example.demo.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.dto.JobDTO;
import com.example.demo.model.JobLevel;
import com.example.demo.model.JobType;
import com.example.demo.request.ApplyJobRequest;
import com.example.demo.request.JobRequest;

public interface JobService {
    JobDTO create(JobRequest request);

    JobDTO update(Long id, JobRequest request);

    void delete(Long id);

    JobDTO getById(Long id);

    Page<JobDTO> search(
            String keyword,
            String location,
            JobType jobType,
            JobLevel level,
            BigDecimal minSalary,
            BigDecimal maxSalary,
            Pageable pageable);

    public List<JobDTO> getJobsOfEmployer();

    public JobDTO createJobForMyCompany(JobRequest request);

    public void applyJob(ApplyJobRequest request);
}