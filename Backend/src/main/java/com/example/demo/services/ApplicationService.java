package com.example.demo.services;

import java.util.List;

import com.example.demo.dto.ApplicationDTO;

public interface ApplicationService {
    public List<ApplicationDTO> getApplicantsOfJob(Long jobId);
}
