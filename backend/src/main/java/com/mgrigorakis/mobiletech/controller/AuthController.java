package com.mgrigorakis.mobiletech.controller;

import com.mgrigorakis.mobiletech.common.dto.ApiResponse;
import com.mgrigorakis.mobiletech.dto.LoginRequest;
import com.mgrigorakis.mobiletech.dto.LoginResponse;
import com.mgrigorakis.mobiletech.dto.RegisterRequest;
import com.mgrigorakis.mobiletech.dto.RegisterResponse;
import com.mgrigorakis.mobiletech.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        return new ApiResponse<>(authService.login(request));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public ApiResponse<RegisterResponse> login(@RequestBody @Valid RegisterRequest request) {
        return new ApiResponse<>(authService.register(request));
    }
}
