package com.mgrigorakis.mobiletech.controller;

import com.mgrigorakis.mobiletech.common.dto.ApiResponse;
import com.mgrigorakis.mobiletech.dto.CategoryRequest;
import com.mgrigorakis.mobiletech.dto.CategoryResponse;
import com.mgrigorakis.mobiletech.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ApiResponse<List<CategoryResponse>> getAllCategories() {
        return new ApiResponse<>(null, categoryService.getAllCategories(), null, null);
    }

    @GetMapping("/{id}")
    public ApiResponse<CategoryResponse> getCategoryById(@PathVariable Long id) {
        return new ApiResponse<>(null, categoryService.getCategoryById(id), null, null);
    }

    @PostMapping
    public ApiResponse<CategoryResponse> createCategory(@RequestBody @Valid CategoryRequest categoryRequest) {
        return new ApiResponse<>(null, categoryService.createCategory(categoryRequest),
                                 null, null);
    }

    @PutMapping("/{id}")
    public ApiResponse<CategoryResponse> updateCategoryById(
            @PathVariable Long id, @RequestBody @Valid CategoryRequest categoryRequest) {
        return new ApiResponse<>(null, categoryService.updateCategoryById(id, categoryRequest),
                                 null, null);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
    }
}
