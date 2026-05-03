package com.mgrigorakis.mobiletech.service;

import com.mgrigorakis.mobiletech.common.dto.PageFilterRequest;
import com.mgrigorakis.mobiletech.common.dto.PageSortRequest;
import com.mgrigorakis.mobiletech.common.exception.ResourceNotFoundException;
import com.mgrigorakis.mobiletech.dto.ProductRequest;
import com.mgrigorakis.mobiletech.dto.ProductResponse;
import com.mgrigorakis.mobiletech.mapper.ProductMapper;
import com.mgrigorakis.mobiletech.model.Category;
import com.mgrigorakis.mobiletech.model.Product;
import com.mgrigorakis.mobiletech.repository.CategoryRepository;
import com.mgrigorakis.mobiletech.repository.ProductRepository;
import com.mgrigorakis.mobiletech.storage.FileStorageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl  implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final FileStorageService fileStorageService;

    @Value("${app.storage.bucket-prefix-products}")
    private String bucketPrefixProducts;

    @Override
    public Page<ProductResponse> getAllProducts(PageFilterRequest filterRequest, PageSortRequest sortRequest) {
        Pageable pageable = PageRequest.of(filterRequest.page(), filterRequest.size(), sortRequest.createSort());
        Page<Product> productPage = productRepository.findAll(pageable);

        return productPage.map(product -> {
            String imageUrl = fileStorageService.getUrl(bucketPrefixProducts, product.getImageKey());
            return ProductMapper.toResponse(product, imageUrl);
        });
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> {
            log.warn("Product not found with id {}", id);
            return new ResourceNotFoundException("Product not found with id " + id);
        });

        String imageUrl = fileStorageService.getUrl(bucketPrefixProducts, product.getImageKey());
        return ProductMapper.toResponse(product, imageUrl);
    }

    @Transactional
    @Override
    public ProductResponse createProduct(ProductRequest dto) {
        Category category = categoryRepository.findById(dto.categoryId()).orElseThrow(() -> {
            log.warn("Category not found with id {}", dto.categoryId());
            return new ResourceNotFoundException("Category not found with id " + dto.categoryId());
        });

        Product product = ProductMapper.toEntity(dto, category);
        Product savedProduct = productRepository.save(product);
        return ProductMapper.toResponse(savedProduct, "");
    }

    @Transactional
    @Override
    public ProductResponse updateProductById(Long id, ProductRequest dto) {
        Product product = productRepository.findById(id).orElseThrow(() -> {
            log.warn("Product not found with id {}", id);
            return new ResourceNotFoundException("Product not found with id " + id);
        });

        Category category = categoryRepository.findById(dto.categoryId()).orElseThrow(() -> {
            log.warn("Category not found with id {}", dto.categoryId());
            return new ResourceNotFoundException("Category not found with id " + dto.categoryId());
        });

        product.setBrand(dto.brand());
        product.setName(dto.name());
        product.setPrice(dto.price());
        product.setStock(dto.stock());
        product.setDescription(dto.description());
        product.setCategory(category);
        Product updatedProduct = productRepository.save(product);

        return ProductMapper.toResponse(updatedProduct, "");
    }

    @Transactional
    @Override
    public void deleteProductById(Long id) {
        if(!productRepository.existsById(id)) {
            log.warn("Product not found with id {}", id);
            throw new ResourceNotFoundException("Product not found with id " + id);
        }

        productRepository.deleteById(id);
    }
}
