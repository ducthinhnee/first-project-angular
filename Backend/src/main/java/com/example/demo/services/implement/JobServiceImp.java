package com.example.demo.services.implement;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.JobDTO;
import com.example.demo.mapper.JobMapper;
import com.example.demo.model.Application;
import com.example.demo.model.ApplicationStatus;
import com.example.demo.model.Company;
import com.example.demo.model.Job;
import com.example.demo.model.JobLevel;
import com.example.demo.model.JobStatus;
import com.example.demo.model.JobType;
import com.example.demo.model.Resume;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.ApplicationRepository;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.JobRepository;
import com.example.demo.repository.ResumeRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.ApplyJobRequest;
import com.example.demo.request.JobRequest;
import com.example.demo.services.JobService;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobServiceImp implements JobService {
    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;
    private final JobMapper jobMapper;
    private final ApplicationRepository applicationRepository;
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;

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

    @Override
    public List<JobDTO> getJobsOfEmployer() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Company company = companyRepository.findByUserEmail(username)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Company not found with username: " + username));

        return jobRepository.findByCompanyIdOrderByCreatedAtDesc(company.getId())
                .stream()
                .map(job -> {
                    JobDTO jobDTO = jobMapper.toDTO(job);
                    long count = applicationRepository.countByJobId(job.getId());
                    jobDTO.setTotalApplicants(count);
                    return jobDTO;
                })
                .toList();
    }

    public JobDTO createJobForMyCompany(JobRequest request) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Company company = companyRepository.findByUserEmail(username)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Company not found with username: " + username));

        Job job = new Job();
        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setLocation(request.getLocation());
        job.setSalaryMin(request.getSalaryMin());
        job.setSalaryMax(request.getSalaryMax());
        job.setJobType(request.getJobType());
        job.setLevel(request.getLevel());
        job.setStatus(JobStatus.OPEN);
        job.setCompany(company);
        job.setCreatedAt(LocalDateTime.now());

        return jobMapper.toDTO(jobRepository.save(job));
    }

    @Override
    @Transactional
    public void applyJob(ApplyJobRequest request) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getRole() != Role.CANDIDATE) {
            throw new AccessDeniedException("Only candidate can apply job");
        }

        Job job = jobRepository.findById(request.getJobId())
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));

        if (job.getStatus() != JobStatus.OPEN) {
            throw new BadRequestException("Job is not open");
        }

        if (applicationRepository.existsByJobIdAndCandidateId(job.getId(), user.getId())) {
            throw new BadRequestException("You already applied this job");
        }

        Resume resume = resumeRepository.findById(request.getResumeId())
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found"));

        Application application = Application.builder()
                .job(job)
                .candidate(user)
                .resume(resume)
                .status(ApplicationStatus.APPLIED)
                .appliedAt(LocalDateTime.now())
                .build();

        applicationRepository.save(application);
    }
}
