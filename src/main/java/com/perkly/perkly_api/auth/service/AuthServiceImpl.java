package com.perkly.perkly_api.auth.service;

import com.perkly.perkly_api.user.domain.User;
import com.perkly.perkly_api.auth.dto.*;
import com.perkly.perkly_api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponse register(RegisterRequest request) {

        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role("USER")
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user);

        return new AuthResponse(token);
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user);

        return new AuthResponse(token);
    }
}