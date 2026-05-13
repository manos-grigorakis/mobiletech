package com.mgrigorakis.mobiletech.dto;

import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public record ProductUpdateRequest(
        @NotBlank(message = "Brand is required")
        @Size(max = 50)
        String brand,

        @NotBlank(message = "Name is required")
        @Size(max = 150)
        String name,

        @NotNull(message = "Price is required")
        @Digits(integer = 17, fraction = 2)
        @Positive
        BigDecimal price,

        @NotNull(message = "Stock is required")
        @PositiveOrZero
        Integer stock,
        String description,

        MultipartFile image,

        @NotNull(message = "Category ID is required")
        Long categoryId
) {}
