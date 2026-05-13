package com.mgrigorakis.mobiletech.service;

import com.mgrigorakis.mobiletech.common.dto.PageFilterRequest;
import com.mgrigorakis.mobiletech.common.dto.PageSortRequest;
import com.mgrigorakis.mobiletech.common.exception.BadRequestException;
import com.mgrigorakis.mobiletech.common.exception.ConflictException;
import com.mgrigorakis.mobiletech.common.exception.ResourceNotFoundException;
import com.mgrigorakis.mobiletech.dto.ProductCreateRequest;
import com.mgrigorakis.mobiletech.dto.ProductResponse;
import com.mgrigorakis.mobiletech.dto.ProductUpdateRequest;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

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
    public Page<ProductResponse> getAllProducts(
            PageFilterRequest filterRequest, PageSortRequest sortRequest, String category) {
        Pageable pageable = PageRequest.of(filterRequest.page(), filterRequest.size(), sortRequest.createSort());
        Page<Product> productPage = (category != null && !category.isBlank())
                ? productRepository.findProductByCategory_Slug(category, pageable)
                : productRepository.findAll(pageable);

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
    public ProductResponse createProduct(ProductCreateRequest dto) {
        Category category = categoryRepository.findById(dto.categoryId()).orElseThrow(() -> {
            log.warn("Category not found with id {}", dto.categoryId());
            return new ResourceNotFoundException("Category not found with id " + dto.categoryId());
        });

        validateFileType(dto.image());

        try {
            String fileName = UUID.randomUUID() + ".webp";
            String key = bucketPrefixProducts + "/" + fileName;
            fileStorageService.store(key, dto.image().getBytes(), dto.image().getContentType());

            Product product = ProductMapper.toEntity(dto, category);
            product.setImageKey(fileName);
            Product savedProduct = productRepository.save(product);
            String imageUrl = fileStorageService.getUrl(bucketPrefixProducts, savedProduct.getImageKey());
            return ProductMapper.toResponse(savedProduct, imageUrl);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }
    }

    @Transactional
    @Override
    public ProductResponse updateProductById(Long id, ProductUpdateRequest dto) {
        Product product = productRepository.findById(id).orElseThrow(() -> {
            log.warn("Product not found with id {}", id);
            return new ResourceNotFoundException("Product not found with id " + id);
        });



        Category category = categoryRepository.findById(dto.categoryId()).orElseThrow(() -> {
            log.warn("Category not found with id {}", dto.categoryId());
            return new ResourceNotFoundException("Category not found with id " + dto.categoryId());
        });

        try {
            if(dto.image() != null && !dto.image().isEmpty()) {
                validateFileType(dto.image());
                // Store to S3
                String key = bucketPrefixProducts + "/" + product.getImageKey();
                fileStorageService.store(key, dto.image().getBytes(), dto.image().getContentType());
            }
            
            // Update fields
            product.setBrand(dto.brand());
            product.setName(dto.name());
            product.setPrice(dto.price());
            product.setStock(dto.stock());
            product.setDescription(dto.description());
            product.setCategory(category);

            // Persist to DB
            Product updatedProduct = productRepository.save(product);
            String imageUrl = fileStorageService.getUrl(bucketPrefixProducts, updatedProduct.getImageKey());
            return ProductMapper.toResponse(updatedProduct, imageUrl);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    /**
     * Validates that the provided {@link MultipartFile} has the {@code .webp} extension
     * @param file The file which will be validated
     * @throws ConflictException If the file extensions is invalid
     */
    private void validateFileType(MultipartFile file) {
        String fileName = file.getOriginalFilename();

        if(fileName == null || !fileName.endsWith(".webp")) {
            throw new BadRequestException(
                    "Invalid file format. Only .webp files are supported", "UNSUPPORTED_FILE_TYPE");
        }
    }
}
