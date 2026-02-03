package com.example.demo.request;

import java.util.Set;

import com.example.demo.dto.SkillDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateProfileRequest {
    private String fullName;
    private String phone;
    private String summary;
    private Set<SkillDTO> skills;
}
