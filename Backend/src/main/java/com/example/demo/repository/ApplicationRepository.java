package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    long countByJobId(Long jobId);

    @Query("""
                SELECT DISTINCT a
                FROM Application a
                JOIN FETCH a.candidate c
                JOIN FETCH c.candidateProfile cp
                LEFT JOIN FETCH cp.skills s
                JOIN FETCH a.resume r
                JOIN a.job j
                JOIN j.company comp
                WHERE j.id = :jobId
                AND comp.id = :companyId
        """)
    List<Application> findApplicationsOfCompanyJob(
            @Param("jobId") Long jobId,
            @Param("companyId") Long companyId);

    boolean existsByJobIdAndCandidateId(Long jobId, Long candidateId);
}
