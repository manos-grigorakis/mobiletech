package com.mgrigorakis.mobiletech.controller;

import com.mgrigorakis.mobiletech.common.dto.ApiResponse;
import com.mgrigorakis.mobiletech.common.dto.PageFilterRequest;
import com.mgrigorakis.mobiletech.common.dto.PageSortRequest;
import com.mgrigorakis.mobiletech.dto.ProductRequest;
import com.mgrigorakis.mobiletech.dto.ProductResponse;
import com.mgrigorakis.mobiletech.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ApiResponse<Page<ProductResponse>> getAllProducts(
            @ModelAttribute @Valid PageFilterRequest filterRequest, @ModelAttribute PageSortRequest sortRequest) {
        return new ApiResponse<>(null, productService.getAllProducts(filterRequest, sortRequest),
                                 null, null);
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getProductById(@PathVariable Long id) {
        return new ApiResponse<>(null, productService.getProductById(id), null, null);
    }

    @PostMapping
    public ApiResponse<ProductResponse> createProduct(@RequestBody @Valid  ProductRequest productRequest) {
        return new ApiResponse<>(null, productService.createProduct(productRequest),
                                 null, null);
    }

    @PutMapping("/{id}")
    public ApiResponse<ProductResponse> updateProductById(@PathVariable Long id,
            @RequestBody @Valid  ProductRequest productRequest) {
        return new ApiResponse<>(null, productService.updateProductById(id, productRequest), null, null);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }
}
