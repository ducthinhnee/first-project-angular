package com.example.demo.request;

import java.math.BigDecimal;

import com.example.demo.model.JobLevel;
import com.example.demo.model.JobType;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class JobRequest {
    private Long companyId;
    private String title;
    private String description;
    private String location;
    private BigDecimal salaryMin;
    private BigDecimal salaryMax;
    private JobType jobType;
    private JobLevel level;
}