package com.perkly.perkly_api.auth.service;

import com.perkly.perkly_api.auth.dto.*;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}