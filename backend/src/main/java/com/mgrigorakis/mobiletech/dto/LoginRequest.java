package com.mgrigorakis.mobiletech.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @Email
        @NotBlank(message = "Email is required")
        @Size(max = 320)
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 100)
        String password
) {}
