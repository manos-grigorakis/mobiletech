package com.mgrigorakis.mobiletech.service;

import com.mgrigorakis.mobiletech.dto.LoginRequest;
import com.mgrigorakis.mobiletech.dto.LoginResponse;
import com.mgrigorakis.mobiletech.dto.RegisterRequest;
import com.mgrigorakis.mobiletech.dto.RegisterResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);

    RegisterResponse register(RegisterRequest request);
}
