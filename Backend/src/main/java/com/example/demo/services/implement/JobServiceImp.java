package com.example.demo.services.implement;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.JobDTO;
import com.example.demo.mapper.JobMapper;
import com.example.demo.model.Company;
import com.example.demo.model.Job;
import com.example.demo.model.JobLevel;
import com.example.demo.model.JobType;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.JobRepository;
import com.example.demo.request.JobRequest;
import com.example.demo.services.JobService;
import com.example.demo.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobServiceImp implements JobService {
    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;
    private final JobMapper jobMapper;

    @Override
    public JobDTO create(JobRequest request) {
        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Company not found with id: " + request.getCompanyId()));

        Job job = jobMapper.toEntity(request);
        job.setCompany(company);

        return jobMapper.toDTO(jobRepository.save(job));
    }

    @Override
    @Transactional
    public JobDTO update(Long id, JobRequest request) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + id));

        jobMapper.updateEntity(job, request);

        if (request.getCompanyId() != null) {
            Company company = companyRepository.findById(request.getCompanyId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Company not found with id: " + request.getCompanyId()));

            job.setCompany(company);
        }
        return jobMapper.toDTO(job);
    }

    @Override
    public void delete(Long id) {
        if (!jobRepository.existsById(id)) {
            throw new ResourceNotFoundException("Job not found with id: " + id);
        }
        jobRepository.deleteById(id);
    }

    @Override
    public Page<JobDTO> search(
            String keyword,
            String location,
            JobType jobType,
            JobLevel level,
            BigDecimal minSalary,
            BigDecimal maxSalary,
            Pageable pageable) {
        return jobRepository.search(
                keyword, location, jobType, level, minSalary, maxSalary, pageable).map(jobMapper::toDTO);
    }

    @Override
    public JobDTO getById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + id));

        return jobMapper.toDTO(job);
    }
}
