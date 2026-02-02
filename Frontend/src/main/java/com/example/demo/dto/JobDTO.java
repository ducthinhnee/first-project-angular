package com.example.demo.dto;

import java.math.BigDecimal;

import com.example.demo.model.JobLevel;
import com.example.demo.model.JobStatus;
import com.example.demo.model.JobType;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class JobDTO {

    private Long id;
    private String title;

    private String companyName;

    private String location;
    private BigDecimal salaryMin;
    private BigDecimal salaryMax;

    private JobType jobType;
    private JobLevel level;
    private JobStatus status;
}

