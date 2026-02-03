package com.example.demo.services.implement;

import com.example.demo.dto.ResumeDTO;
import com.example.demo.exception.FileUploadException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.ResumeMapper;
import com.example.demo.model.CandidateProfile;
import com.example.demo.model.Experience;
import com.example.demo.model.Resume;
import com.example.demo.repository.CandidateProfileRepository;
import com.example.demo.repository.ResumeRepository;
import com.example.demo.services.ResumeService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;
    private final CandidateProfileRepository candidateProfileRepository;
    private final ResumeMapper resumeMapper;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public ResumeServiceImpl(ResumeRepository resumeRepository, CandidateProfileRepository candidateProfileRepository,
            ResumeMapper resumeMapper) {
        this.resumeRepository = resumeRepository;
        this.candidateProfileRepository = candidateProfileRepository;
        this.resumeMapper = resumeMapper;
    }

    @Override
    public ResumeDTO uploadResume(MultipartFile file) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        CandidateProfile profile = candidateProfileRepository.findByUserEmail(userEmail)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Candidate profile not found with email: " + userEmail));

        try {
            if (file.isEmpty()) {
                throw new FileUploadException("File must not be empty");
            }

            // Tạo thư mục nếu chưa có
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Đổi tên file tránh trùng
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            Resume resume = new Resume();
            resume.setProfile(profile);
            resume.setFileUrl("/uploads/" + fileName);
            resumeRepository.save(resume);
            return resumeMapper.toDto(resumeRepository.save(resume));
        } catch (Exception e) {
            throw new FileUploadException("Upload file failed");
        }

    }

    @Override
    public List<ResumeDTO> getResume() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        CandidateProfile profile = candidateProfileRepository.findByUserEmail(username)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Candidate profile not found with email: " + username));

        return resumeRepository.findByProfileId(profile.getId()).stream()
                .map(resumeMapper::toDto)
                .collect(Collectors.toList());
    }

    private void validateOwnership(Long resumeId) {
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found with id: " + resumeId));

        CandidateProfile profile = resume.getProfile();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!profile.getUser().getEmail().equals(username)) {
            throw new AccessDeniedException("You do not have permission to access this resource.");
        }
    }

    @Override
    @Transactional
    public void delete(Long resumeId) {
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found with id: " + resumeId));
        validateOwnership(resumeId);

        String fileUrl = resume.getFileUrl();
        String fileName = Paths.get(fileUrl).getFileName().toString();
        Path filePath = Paths.get(uploadDir).resolve(fileName).toAbsolutePath();

        try {
            Files.deleteIfExists(filePath);
        } catch (Exception e) {
            throw new FileUploadException("Failed to delete resume file", e);
        }

        resumeRepository.delete(resume);
    }
}
