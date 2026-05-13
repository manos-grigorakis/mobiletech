package com.mgrigorakis.mobiletech.mapper;

import com.mgrigorakis.mobiletech.dto.CategorySummaryResponse;
import com.mgrigorakis.mobiletech.dto.ProductCreateRequest;
import com.mgrigorakis.mobiletech.dto.ProductResponse;
import com.mgrigorakis.mobiletech.model.Category;
import com.mgrigorakis.mobiletech.model.Product;

public class ProductMapper {
    // DTO -> Entity
    public static Product toEntity(ProductCreateRequest dto, Category category) {
        return Product.builder()
                .brand(dto.brand())
                .name(dto.name())
                .price(dto.price())
                .stock(dto.stock())
                .description(dto.description())
                .category(category)
                .build();
    }

    public static ProductResponse toResponse(Product product, String imageUrl) {
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
                imageUrl,
                product.getCreatedAt(),
                product.getUpdatedAt(),
                categorySummary
        );
    }
}
