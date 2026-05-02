package com.mgrigorakis.mobiletech.dto;

import java.time.LocalDateTime;

public record CategoryResponse(
        Long id,
        String name,
        String slug,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
