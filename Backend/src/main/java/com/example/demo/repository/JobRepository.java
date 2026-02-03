package com.example.demo.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Job;
import com.example.demo.model.JobLevel;
import com.example.demo.model.JobType;

import org.springframework.data.domain.Page;

public interface JobRepository extends JpaRepository<Job, Long> {
        @Query("""
                SELECT j FROM Job j
                WHERE j.status = 'OPEN'
                AND (:keyword IS NULL OR LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%')))
                AND (:location IS NULL OR LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%')))
                AND (:jobType IS NULL OR j.jobType = :jobType)
                AND (:level IS NULL OR j.level = :level)
                AND (:minSalary IS NULL OR j.salaryMin >= :minSalary)
                AND (:maxSalary IS NULL OR j.salaryMax <= :maxSalary)
        """)
        Page<Job> search(
                        String keyword,
                        String location,
                        JobType jobType,
                        JobLevel level,
                        BigDecimal minSalary,
                        BigDecimal maxSalary,
                        Pageable pageable);
        
                
        List<Job> findByCompanyIdOrderByCreatedAtDesc(Long companyId);
        
}
