package com.mgrigorakis.mobiletech.dto;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;

public record ProductRequest(
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

        @NotBlank(message = "Image URL is required")
        @Size(max = 500)
        @URL
        String imageUrl,

        @NotNull(message = "Category ID is required")
        Long categoryId
) {
}
