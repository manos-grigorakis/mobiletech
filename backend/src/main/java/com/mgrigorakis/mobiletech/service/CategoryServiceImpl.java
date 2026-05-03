package com.mgrigorakis.mobiletech.service;

import com.mgrigorakis.mobiletech.common.exception.ConflictException;
import com.mgrigorakis.mobiletech.common.exception.ResourceNotFoundException;
import com.mgrigorakis.mobiletech.dto.CategoryRequest;
import com.mgrigorakis.mobiletech.dto.CategoryResponse;
import com.mgrigorakis.mobiletech.mapper.CategoryMapper;
import com.mgrigorakis.mobiletech.model.Category;
import com.mgrigorakis.mobiletech.repository.CategoryRepository;
import com.mgrigorakis.mobiletech.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(CategoryMapper::toResponse).toList();
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> {
            log.error("Category with id {} not found", id);
            return new ResourceNotFoundException("Category with id " + id + " not found");
        });

        return CategoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest dto) {
        Category category = CategoryMapper.toEntity(dto);
        validateUnique(category.getName(), category.getSlug(), category.getId());

        Category savedCategory = categoryRepository.save(category);
        return CategoryMapper.toResponse(savedCategory);
    }

    @Override
    public CategoryResponse updateCategoryById(Long id, CategoryRequest dto) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> {
            log.error("Category with id {} not found", id);
            return new ResourceNotFoundException("Category with id " + id + " not found");
        });

        validateUnique(category.getName(), category.getSlug(), category.getId());

        category.setName(dto.name());
        category.setSlug(dto.slug());
        Category savedCategory = categoryRepository.save(category);

        return CategoryMapper.toResponse(savedCategory);
    }

    @Override
    public void deleteCategoryById(Long id) {
        if (!categoryRepository.existsById(id)) {
            log.error("Category with id {} not found", id);
            throw new ResourceNotFoundException("Category with id " + id + " not found");
        }

        if(productRepository.existsByCategoryId(id)) {
            log.warn("Cannot delete category {} because it has associated products", id);
            throw new ConflictException("Category cannot be deleted because it has associated products",
                                        "CATEGORY_HAS_PRODUCTS");
        }

        categoryRepository.deleteById(id);
    }

    /**
     * Validates that there is not a category with the same {@code name} or {@code slug}
     * @param name The {@link Category#name}
     * @param slug The {@link Category#slug}
     * @param id The {@link Category#id}
     */
    private void validateUnique(String name, String slug, Long id) {
        Optional<Category> existsByName = categoryRepository.findByName(name);

        if(existsByName.isPresent()) {
            Category category = existsByName.get();

            if(!category.getName().equals(name)) {
                log.warn("Category with name {} already exists", name);
                throw new ConflictException("Category with name " + name + " already exists", "CATEGORY_ΝΑΜΕ_EXISTS");
            }
        }

        Optional<Category> existsBySlug = categoryRepository.findBySlug(slug);

        if(existsBySlug.isPresent()) {
            Category category = existsBySlug.get();

            if(!category.getSlug().equals(slug)) {
                log.warn("Category with slug {} already exists", slug);
                throw new ConflictException("Category with slug " + slug + " already exists",  "CATEGORY_SLUG_EXISTS");
            }
        }
    }
}
