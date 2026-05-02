package com.mgrigorakis.mobiletech.service;

import com.mgrigorakis.mobiletech.dto.CategoryRequest;
import com.mgrigorakis.mobiletech.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(Long id);

    CategoryResponse createCategory(CategoryRequest dto);

    CategoryResponse updateCategoryById(Long id, CategoryRequest dto);

    void deleteCategoryById(Long id);
}
