package com.example.demo.services.implement;

import com.example.demo.config.JwtUtil;
import com.example.demo.exception.EmailAlreadyExistsException;
import com.example.demo.exception.InvalidCredentialsException;
import com.example.demo.model.User;
import com.example.demo.model.CandidateProfile;
import com.example.demo.model.Company;
import com.example.demo.model.Role;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.CandidateProfileRepository;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.request.LoginRequest;
import com.example.demo.request.RegisterRequest;
import com.example.demo.response.AuthResponse;
import com.example.demo.services.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final CandidateProfileRepository candidateProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final CompanyRepository companyRepository;

    @Override
    public AuthResponse register(RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists: " + registerRequest.getEmail());
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(registerRequest.getRole());
        userRepository.save(user);

        // Create empty CandidateProfile for the new user
        if (registerRequest.getRole() == Role.CANDIDATE) {
            CandidateProfile candidateProfile = new CandidateProfile();
            candidateProfile.setUser(user);
            candidateProfileRepository.save(candidateProfile);
        } else if (registerRequest.getRole() == Role.EMPLOYER) {
            Company company = new Company();
            company.setUser(user);
            company.setName("");
            companyRepository.save(company);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(registerRequest.getEmail());
        String token = jwtUtil.generateToken(userDetails);

        return new AuthResponse(token, "Registration successful", user.getRole());
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        User user = userRepository.findByEmail(loginRequest.getEmail()).get();
        String token = jwtUtil.generateToken(userDetails);

        return new AuthResponse(token, "Login successful", user.getRole());
    }
}
