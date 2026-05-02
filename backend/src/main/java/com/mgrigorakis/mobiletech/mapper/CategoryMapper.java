package com.mgrigorakis.mobiletech.mapper;

import com.mgrigorakis.mobiletech.dto.CategoryRequest;
import com.mgrigorakis.mobiletech.dto.CategoryResponse;
import com.mgrigorakis.mobiletech.model.Category;

public class CategoryMapper {
    // DTO -> Model
    public static Category toModel(CategoryRequest dto) {
        return Category.builder()
                .name(dto.name())
                .slug(dto.slug())
                .build();
    }

    // Model -> Response
    public static CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.getId(), category.getName(), category.getSlug(), category.getCreatedAt(),
                category.getUpdatedAt());
    }
}
