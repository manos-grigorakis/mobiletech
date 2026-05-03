package com.mgrigorakis.mobiletech.controller;

import com.mgrigorakis.mobiletech.common.dto.ApiResponse;
import com.mgrigorakis.mobiletech.common.dto.PageFilterRequest;
import com.mgrigorakis.mobiletech.common.dto.PageSortRequest;
import com.mgrigorakis.mobiletech.dto.ProductRequest;
import com.mgrigorakis.mobiletech.dto.ProductResponse;
import com.mgrigorakis.mobiletech.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ApiResponse<Page<ProductResponse>> getAllProducts(
            @ModelAttribute @Valid PageFilterRequest filterRequest, @ModelAttribute PageSortRequest sortRequest,
            @RequestParam(name = "category", required = false) String category) {
        return new ApiResponse<>(productService.getAllProducts(filterRequest, sortRequest, category));
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getProductById(@PathVariable Long id) {
        return new ApiResponse<>(productService.getProductById(id));
    }

    @PostMapping
    public ApiResponse<ProductResponse> createProduct(@ModelAttribute @Valid ProductRequest productRequest) {
        return new ApiResponse<>(productService.createProduct(productRequest));
    }

    @PutMapping("/{id}")
    public ApiResponse<ProductResponse> updateProductById(
            @PathVariable Long id, @ModelAttribute @Valid ProductRequest productRequest) {
        return new ApiResponse<>(productService.updateProductById(id, productRequest));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }
}
