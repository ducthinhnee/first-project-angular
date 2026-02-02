package com.example.demo.dto;

import lombok.Data;
import java.util.Date;

@Data
public class ExperienceDTO {
    private Long id;
    private Long profileId;
    private String companyName;
    private String position;
    private Date startDate;
    private Date endDate;
}
