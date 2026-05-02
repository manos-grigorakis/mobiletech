package com.mgrigorakis.mobiletech.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest(
        @NotBlank(message = "Name is required")
        @Size(max = 50)
        String name,

        @NotBlank(message = "Slug is required")
        @Size(max = 50)
        String slug
) {}
