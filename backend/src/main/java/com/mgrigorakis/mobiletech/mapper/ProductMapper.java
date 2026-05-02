package com.mgrigorakis.mobiletech.mapper;

import com.mgrigorakis.mobiletech.dto.CategorySummaryResponse;
import com.mgrigorakis.mobiletech.dto.ProductRequest;
import com.mgrigorakis.mobiletech.dto.ProductResponse;
import com.mgrigorakis.mobiletech.model.Category;
import com.mgrigorakis.mobiletech.model.Product;

public class ProductMapper {
    // DTO -> Entity
    public static Product toEntity(ProductRequest dto, Category category) {
        return Product.builder()
                .brand(dto.brand())
                .name(dto.name())
                .price(dto.price())
                .stock(dto.stock())
                .description(dto.description())
                .imageUrl(dto.imageUrl())
                .category(category)
                .build();
    }

    public static ProductResponse toResponse(Product product) {
        Category category = product.getCategory();
        CategorySummaryResponse categorySummary = new CategorySummaryResponse(
                category.getId(), category.getName(), category.getSlug());

        return new ProductResponse(
                product.getId(),
                product.getBrand(),
                product.getName(),
                product.getPrice(),
                product.getStock(),
                product.getDescription(),
                product.getImageUrl(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                categorySummary
        );
    }
}
