package com.example.demo.request;

import java.math.BigDecimal;

public interface JobWithApplicantCount {
    Long getId();
    String getTitle();
    String getLocation();
    BigDecimal getSalaryMin();
    BigDecimal getSalaryMax();
    String getStatus();
    Long getTotalApplicants();
}
