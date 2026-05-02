package com.mgrigorakis.mobiletech.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponse (
        Long id,
        String brand,
        String name,
        BigDecimal price,
        Integer stock,
        String description,
        String imageUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        CategorySummaryResponse category
) {}
