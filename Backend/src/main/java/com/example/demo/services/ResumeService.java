package com.example.demo.services;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.ResumeDTO;

public interface ResumeService {
    public List<ResumeDTO> getResume();

    public void delete(Long resumeId);

    public ResumeDTO uploadResume(MultipartFile file);

    public ResponseEntity<Resource> downloadResume(String fileName);
}
