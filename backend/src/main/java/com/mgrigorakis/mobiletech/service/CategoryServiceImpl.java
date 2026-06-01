package com.mgrigorakis.mobiletech.service;

import com.mgrigorakis.mobiletech.common.exception.ConflictException;
import com.mgrigorakis.mobiletech.common.exception.ResourceNotFoundException;
import com.mgrigorakis.mobiletech.dto.CategoryRequest;
import com.mgrigorakis.mobiletech.dto.CategoryResponse;
import com.mgrigorakis.mobiletech.mapper.CategoryMapper;
import com.mgrigorakis.mobiletech.model.Category;
import com.mgrigorakis.mobiletech.repository.CategoryRepository;
import com.mgrigorakis.mobiletech.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Log4j2
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Cacheable(value = "categories", key = "'all'")
    @Override
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(CategoryMapper::toResponse).toList();
    }

    @Cacheable(value = "categories", key = "#id")
    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> {
            log.error("Category with id {} not found", id);
            return new ResourceNotFoundException("Category with id " + id + " not found");
        });

        return CategoryMapper.toResponse(category);
    }

    @CacheEvict(value = "categories", key = "'all'")
    @Override
    public CategoryResponse createCategory(CategoryRequest dto) {
        Category category = CategoryMapper.toEntity(dto);
        validateUnique(category.getName(), category.getSlug(), null);

        Category savedCategory = categoryRepository.save(category);
        return CategoryMapper.toResponse(savedCategory);
    }

    @Caching(evict = {
            @CacheEvict(value = "categories", key = "#id"),
            @CacheEvict(value = "categories", key = "'all'")
    })
    @Override
    public CategoryResponse updateCategoryById(Long id, CategoryRequest dto) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> {
            log.error("Category with id {} not found", id);
            return new ResourceNotFoundException("Category with id " + id + " not found");
        });

        validateUnique(dto.name(), dto.slug(), id);

        category.setName(dto.name());
        category.setSlug(dto.slug());
        Category savedCategory = categoryRepository.save(category);

        return CategoryMapper.toResponse(savedCategory);
    }

    @Caching(evict = {
            @CacheEvict(value = "categories", key = "#id"),
            @CacheEvict(value = "categories", key = "'all'")
    })
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
        categoryRepository.findByName(name).filter(c -> !Objects.equals(c.getId(), id))
                .ifPresent(c -> {
                    throw new ConflictException("Category with name " + name + " already exists", "CATEGORY_ΝΑΜΕ_EXISTS");
                });

        categoryRepository.findBySlug(slug).filter(c -> !Objects.equals(c.getId(), id))
                .ifPresent(c -> {
                    throw new ConflictException("Category with slug " + slug + " already exists", "CATEGORY_SLUG_EXISTS");
        });
    }
}
